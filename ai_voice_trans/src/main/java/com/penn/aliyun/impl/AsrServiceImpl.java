package com.penn.aliyun.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.penn.aliyun.AsrService;
import com.penn.constant.Constant;
import com.penn.factory.IAcsClientFactory;
import org.springframework.stereotype.Service;

@Service("asrService")
public class AsrServiceImpl implements AsrService {


    @Override
    public String submitFileTransRequest(String fileLink) {

        CommonRequest postRequest = new CommonRequest();
        // 设置域名
        postRequest.setDomain(Constant.DOMAIN);
        // 设置API的版本号，格式为YYYY-MM-DD
        postRequest.setVersion(Constant.API_VERSION);
        // 设置action
        postRequest.setAction(Constant.POST_REQUEST_ACTION);
        // 设置产品名称
        postRequest.setProduct(Constant.PRODUCT);
        /**
         * 2. 设置录音文件识别请求参数，以JSON字符串的格式设置到请求的Body中
         */
        JSONObject taskObject = new JSONObject();
        // 设置appkey
        taskObject.put(Constant.KEY_APP_KEY, Constant.APP_KEY);
        // 设置音频文件访问链接
        taskObject.put(Constant.KEY_FILE_LINK, fileLink);
        // 新接入请使用4.0版本，已接入(默认2.0)如需维持现状，请注释掉该参数设置
        taskObject.put(Constant.KEY_VERSION, "4.0");
        // 设置是否输出词信息，默认为false，开启时需要设置version为4.0及以上
        taskObject.put(Constant.KEY_ENABLE_WORDS, true);
        String task = taskObject.toJSONString();
        System.out.println(task);
        // 设置以上JSON字符串为Body参数
        postRequest.putBodyParameter(Constant.KEY_TASK, task);
        // 设置为POST方式的请求
        postRequest.setMethod(MethodType.POST);
        /**
         * 3. 提交录音文件识别请求，获取录音文件识别请求任务的ID，以供识别结果查询使用
         */
        String taskId = null;
        try {
            IAcsClient client = IAcsClientFactory.getInstance();
            CommonResponse postResponse = client.getCommonResponse(postRequest);
            System.err.println("提交录音文件识别请求的响应：" + postResponse.getData());
            if (postResponse.getHttpStatus() == 200) {
                JSONObject result = JSONObject.parseObject(postResponse.getData());
                String statusText = result.getString(Constant.KEY_STATUS_TEXT);
                if (statusText.equals(Constant.STATUS_SUCCESS)) {
                    taskId = result.getString(Constant.KEY_TASK_ID);
                }
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return taskId;
    }

    @Override
    public String getFileTransResult(String taskId) {

        CommonRequest getRequest = new CommonRequest();
        // 设置域名
        getRequest.setDomain(Constant.DOMAIN);
        // 设置API版本
        getRequest.setVersion(Constant.API_VERSION);
        // 设置action
        getRequest.setAction(Constant.GET_REQUEST_ACTION);
        // 设置产品名称
        getRequest.setProduct(Constant.PRODUCT);
        // 设置任务ID为查询参数
        getRequest.putQueryParameter(Constant.KEY_TASK_ID, taskId);
        // 设置为GET方式的请求
        getRequest.setMethod(MethodType.GET);
        /**
         * 2. 提交录音文件识别结果查询请求
         * 以轮询的方式进行识别结果的查询，直到服务端返回的状态描述为“SUCCESS”,或者为错误描述，则结束轮询。
         */
        String result = null;
        while (true) {
            try {
                IAcsClient client = IAcsClientFactory.getInstance();
                CommonResponse getResponse = client.getCommonResponse(getRequest);
                System.err.println("识别查询结果：" + getResponse.getData());
                if (getResponse.getHttpStatus() != 200) {
                    break;
                }
                JSONObject rootObj = JSONObject.parseObject(getResponse.getData());
                String statusText = rootObj.getString(Constant.KEY_STATUS_TEXT);
                if (statusText.equals(Constant.STATUS_RUNNING) || statusText.equals(Constant.STATUS_QUEUEING)) {
                    // 继续轮询，注意设置轮询时间间隔
                    Thread.sleep(3000);
                }
                else {
                    // 状态信息为成功，返回识别结果；状态信息为异常，返回空
                    if (statusText.equals(Constant.STATUS_SUCCESS)) {
                        result = rootObj.getString(Constant.KEY_RESULT);
                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
