package com.penn.aliyun;

public interface AsrService {

    //申请转文字
    String submitFileTransRequest(String fileLink);
    //查询转换结果
    String getFileTransResult(String taskId);

}
