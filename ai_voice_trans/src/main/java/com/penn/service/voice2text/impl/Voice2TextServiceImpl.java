package com.penn.service.voice2text.impl;

import com.penn.service.voice2text.Voice2TextService;
import com.penn.vo.Voice2TextVo;
import org.springframework.stereotype.Service;

@Service("voice2TextService")
public class Voice2TextServiceImpl implements Voice2TextService {

    @Override
    public Voice2TextVo queryInitVoice() {
        //TODO 待接入数据库
        //每次查询一条当天最新数据
        Voice2TextVo vo = new Voice2TextVo();
        vo.setCallId("1231321231");
        vo.setFilePath("https://aliyun-nls.oss-cn-hangzhou.aliyuncs.com/asr/fileASR/examples/nls-sample-16k.wav");
        return vo;
    }

    @Override
    public Voice2TextVo queryProcessingVoice() {
        Voice2TextVo vo = new Voice2TextVo();
        vo.setTaskId("ec7247ff9fb511e999b8615677084909");
        return vo;
    }


    @Override
    public int updateVoiceTaskId(Voice2TextVo textVo) {
        //TODO
        return 1;
    }


    @Override
    public int updateVoiceTaskResult(Voice2TextVo textVo) {
        //TODO
        return 1;
    }

}
