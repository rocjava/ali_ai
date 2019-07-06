package com.penn.service.voice2text;

import com.penn.vo.Voice2TextVo;

public interface Voice2TextService {

    /**
     * 查询待转换语音
     * @return Voice2TextVo
     */
    Voice2TextVo queryInitVoice ();


    Voice2TextVo queryProcessingVoice();

    /**
     * 保存语音转文字任务taskId
     * @param textVo
     */
    int updateVoiceTaskId(Voice2TextVo textVo);

    /**
     * 保存语音转文字任务result
     * @param textVo
     */
    int updateVoiceTaskResult(Voice2TextVo textVo);



}
