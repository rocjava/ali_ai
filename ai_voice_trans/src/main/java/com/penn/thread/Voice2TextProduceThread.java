package com.penn.thread;

import com.penn.service.task.TaskService;

public class Voice2TextProduceThread extends Thread {

    private TaskService taskService;

    public Voice2TextProduceThread(TaskService taskService){
        this.taskService = taskService;
    }

    public void run(){
        taskService.produce();
    }
}
