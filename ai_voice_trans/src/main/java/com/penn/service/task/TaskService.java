package com.penn.service.task;

import com.penn.vo.Voice2TextVo;

public interface TaskService {

    void produce();

    void consume(Voice2TextVo voice2TextVo);

}
