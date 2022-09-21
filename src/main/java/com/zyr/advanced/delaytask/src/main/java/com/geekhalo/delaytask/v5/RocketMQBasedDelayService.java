package com.geekhalo.delaytask.v5;

import com.geekhalo.delaytask.service.OrderInfoService;
import com.geekhalo.delaytask.v5.support.RocketMQBasedDelay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RocketMQBasedDelayService {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 通过 RocketMQBasedDelay 指定方法为延时方法，该 注解做两件事：<br />
     * 1. 基于 AOP 技术，拦截对 cancelOrder 的调用，将参数转为为 Message， 并发送到 RocketMQ 的延时队列
     * 2. 针对 cancelOrder 方法，创建 DefaultMQPushConsumer 并订阅相关消息，进行消息处理
     * @param orderId
     */
    @RocketMQBasedDelay(topic = "delay-task-topic-ann",
            delayLevel = 2, consumerGroup = "CancelOrderGroup")
    public void cancelOrder(Long orderId){
        if (orderId == null){
            log.info("param is invalidate");
            return;
        }
        this.orderInfoService.cancel(orderId);
        log.info("success to cancel Order for {}", orderId);
    }
}
