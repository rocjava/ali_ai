package com.penn.service.aliyun.callback;

import com.alibaba.fastjson.JSONObject;
import com.penn.constant.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;

/**
 *  @Description 该服务用于回调方式获取转写结果
 *  @Author zhangxiaopeng
 *  @Date 2019-07-05
 *
 */
@RequestMapping("/filetrans/callback")
@RestController
public class FiletransCallBackController {

    // 必须是post的方式
    @RequestMapping(value = "result", method = RequestMethod.POST)
    public void GetResult(HttpServletRequest request) {
        byte [] buffer = new byte[request.getContentLength()];
        ServletInputStream in = null;
        try {
            in = request.getInputStream();
            in.read(buffer, 0 ,request.getContentLength());
            in.close();
            // 获取json格式的文件转写结果
            String result = new String(buffer);
            JSONObject jsonResult = JSONObject.parseObject(result);
            // 解析并输出相关结果内容
            System.out.println("获取文件中转写回调结果:" + result);
            System.out.println("TaskId: " + jsonResult.getString("TaskId"));
            System.out.println("StatusCode: " + jsonResult.getString("StatusCode"));
            System.out.println("StatusText: " + jsonResult.getString("StatusText"));
            Matcher matcherClient = Constant.PATTERN_CLIENT_ERR.matcher(jsonResult.getString("StatusCode"));
            Matcher matcherServer =  Constant.PATTERN_SERVER_ERR.matcher(jsonResult.getString("StatusCode"));
            // 以2开头状态码为正常状态码，回调方式方式正常状态只返回"21050000"
            if(jsonResult.getString("StatusCode").equals("21050000")) {
                System.out.println("RequestTime: " + jsonResult.getString("RequestTime"));
                System.out.println("SolveTime: " + jsonResult.getString("SolveTime"));
                System.out.println("BizDuration: " + jsonResult.getString("BizDuration"));
                System.out.println("Result.Sentences.size: " +
                        jsonResult.getJSONObject("Result").getJSONArray("Sentences").size());
                for (int i = 0; i < jsonResult.getJSONObject("Result").getJSONArray("Sentences").size(); i++) {
                    System.out.println("Result.Sentences[" + i + "].BeginTime: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("BeginTime"));
                    System.out.println("Result.Sentences[" + i + "].EndTime: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("EndTime"));
                    System.out.println("Result.Sentences[" + i + "].SilenceDuration: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("SilenceDuration"));
                    System.out.println("Result.Sentences[" + i + "].Text: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("Text"));
                    System.out.println("Result.Sentences[" + i + "].ChannelId: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("ChannelId"));
                    System.out.println("Result.Sentences[" + i + "].SpeechRate: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("SpeechRate"));
                    System.out.println("Result.Sentences[" + i + "].EmotionValue: " +
                            jsonResult.getJSONObject("Result").getJSONArray("Sentences").getJSONObject(i).getString("EmotionValue"));
                }
            }
            else if(matcherClient.matches()) {
                System.out.println("客户端错误");
            }
            else if(matcherServer.matches()) {
                System.out.println("服务端错误");
            }
            else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
