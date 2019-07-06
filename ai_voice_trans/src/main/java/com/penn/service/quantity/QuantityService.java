package com.penn.service.quantity;

import com.penn.vo.Voice2TextVo;

public interface QuantityService {

    /**
     * 查询待转换语音
     * @return Voice2TextVo
     */
    Voice2TextVo queryVoiceToBeTransformed ();


}
