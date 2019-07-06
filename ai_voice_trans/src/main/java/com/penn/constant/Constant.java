package com.penn.constant;

import java.util.regex.Pattern;

public class Constant {

    public static final String ACCESS_KEY_ID = "LTAIiCHKjAkMj7Cd";
    public static final String ACCESS_KEY_SECRET = "knDwJR66Wxp8Xtube1PIzmcUTaGhYR";
    public static final String APP_KEY = "roGumIbgliMkuXvt";


    // 地域ID，常量内容，请勿改变
    public static final String REGIONID = "cn-shanghai";
    public static final String ENDPOINTNAME = "cn-shanghai";
    public static final String PRODUCT = "nls-filetrans";
    public static final String DOMAIN = "filetrans.cn-shanghai.aliyuncs.com";

    public static final String API_VERSION = "2018-08-17";
    public static final String POST_REQUEST_ACTION = "SubmitTask";
    public static final String GET_REQUEST_ACTION = "GetTaskResult";
    // 请求参数key
    public static final String KEY_APP_KEY = "appkey";
    public static final String KEY_FILE_LINK = "file_link";
    public static final String KEY_VERSION = "version";
    public static final String KEY_ENABLE_WORDS = "enable_words";
    // 响应参数key
    public static final String KEY_TASK = "Task";
    public static final String KEY_TASK_ID = "TaskId";
    public static final String KEY_STATUS_TEXT = "StatusText";
    public static final String KEY_RESULT = "Result";
    // 状态值
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_RUNNING = "RUNNING";
    public static final String STATUS_QUEUEING = "QUEUEING";

    // 以4开头的状态码是客户端错误
    public static final Pattern PATTERN_CLIENT_ERR = Pattern.compile("4105[0-9]*");
    // 以5开头的状态码是服务端错误
    public static final Pattern PATTERN_SERVER_ERR = Pattern.compile("5105[0-9]*");

    //阿里云语音转移最大并发数  TODO 后面接入到appolo 使用config
    public static final Integer MAX_CONCURRENCY = 60;

}
