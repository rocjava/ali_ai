package com.penn.service.task.impl;

import com.penn.constant.Constant;
import com.penn.service.aliyun.AsrService;
import com.penn.service.voice2text.Voice2TextService;
import com.penn.service.task.TaskService;
import com.penn.vo.Voice2TextVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    //如果部署一台机子可以放在本地，如果是多台机子需要平均分配或者使用Redis分布式锁
    private static AtomicInteger currentSize = new AtomicInteger(0);

    private Lock lock =  new ReentrantLock();

    private Condition produceCondition = lock.newCondition();

    private Condition consumeCondition = lock.newCondition();

    @Autowired
    private Voice2TextService voice2TextService;

    @Autowired
    private AsrService asrService;


    @Override
    public void produce() {
        lock.lock();
        try{
            System.out.println("====>TaskService.produce====>start====>currentSize====>" +currentSize);
            while(currentSize.intValue() >= Constant.MAX_CONCURRENCY){
                Thread.sleep(30000);//睡半分钟 等待去查询
                produceCondition.await();
            }
            Voice2TextVo voice2TextVo = voice2TextService.queryInitVoice();
            System.out.println("====>TaskService.produce====>voice====>" +voice2TextVo.getCallId());
            String taskId = asrService.submitFileTransRequest(voice2TextVo.getFilePath());
            System.out.println("====>TaskService.produce====>voice====>" +voice2TextVo.getCallId() +"====>taskId====>" +taskId);
            if( !StringUtils.isEmpty(taskId)){
                voice2TextVo.setTaskId(taskId);
                voice2TextService.updateVoiceTaskId(voice2TextVo);
            }
            currentSize.getAndIncrement();
            System.out.println("====>TaskService.produce====>end====>currentSize====>" +currentSize);
            consumeCondition.notify();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void consume(Voice2TextVo voice2TextVo) {
        lock.lock();
        try{
            System.out.println("====>TaskService.consume====>start====>currentSize====>" +currentSize);
            while(currentSize.intValue() <= 0){
                consumeCondition.await();
            }
            Voice2TextVo vo = voice2TextService.queryProcessingVoice();
            String result = asrService.getFileTransResult(vo.getTaskId());
            vo.setFileJson(result);
            System.out.println("====>TaskService.produce====>voice====>" +voice2TextVo.getCallId() +"====>taskId====>" +voice2TextVo.getTaskId() +"====>result====>" +result);
            voice2TextService.updateVoiceTaskResult(vo);
            currentSize.getAndDecrement();
            System.out.println("====>TaskService.consume====>end====>currentSize====>" +currentSize);
            produceCondition.notifyAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
