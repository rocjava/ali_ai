package com.penn.test.service;

import com.penn.service.aliyun.AsrService;
import com.penn.test.AsrApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IApiAsrServiceTest extends AsrApplicationTests {
    @Autowired
    private AsrService asrService;

    @Test
    public void testService(){
        String taskId = "";
        String result = "";
        taskId = asrService
                .submitFileTransRequest("https://aliyun-nls.oss-cn-hangzhou.aliyuncs.com/asr/fileASR/examples/nls-sample-16k.wav");
        if(taskId != null){
            System.out.println(taskId);
            result =  asrService.getFileTransResult(taskId);
        }
        if(result != null){
            System.out.println(result);
        }

    }
}
