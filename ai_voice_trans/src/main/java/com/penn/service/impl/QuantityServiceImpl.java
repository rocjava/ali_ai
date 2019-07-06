package com.penn.service.impl;

import com.penn.service.quantity.QuantityService;
import com.penn.vo.Voice2TextVo;
import org.springframework.stereotype.Service;

@Service("quantityService")
public class QuantityServiceImpl implements QuantityService {

    @Override
    public Voice2TextVo queryVoiceToBeTransformed() {
        //TODO 待接入数据库
        //每次查询一条当天最新数据
        Voice2TextVo vo = new Voice2TextVo();
        vo.setCallId("1231321231");
        vo.setFilePath("https://aliyun-nls.oss-cn-hangzhou.aliyuncs.com/asr/fileASR/examples/nls-sample-16k.wav");
        return vo;
    }
}
