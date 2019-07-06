package com.penn.vo;

import java.io.Serializable;

/**
 * @Autho zhangxiaopeng
 * @Description 语音转文字vo类
 * @date 2019-97-06 11:37
 */
public class Voice2TextVo implements Serializable {

    private static final long serialVersionUID = 3274682866077767795L;

    private String callId;

    private String taskId;

    private String filePath;

    private String fileJson;


    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileJson() {
        return fileJson;
    }

    public void setFileJson(String fileJson) {
        this.fileJson = fileJson;
    }
}
