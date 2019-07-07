package com.penn.thread;

import com.penn.service.task.TaskService;
import com.penn.vo.Voice2TextVo;

public class Voice2TextConsumeThread extends Thread {

    private TaskService taskService;

    public Voice2TextConsumeThread(TaskService taskService){
        this.taskService = taskService;
    }

    public void run(){
        taskService.consume(new Voice2TextVo());
    }
}
