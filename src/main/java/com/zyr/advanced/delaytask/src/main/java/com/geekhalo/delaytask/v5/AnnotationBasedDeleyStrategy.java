package com.geekhalo.delaytask.v5;

import com.geekhalo.delaytask.service.OrderInfoCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
public class AnnotationBasedDeleyStrategy {
    @Autowired
    private RocketMQBasedDelayService delayService;

    @TransactionalEventListener
    public void onOrderCreated(OrderInfoCreateEvent event){
        // 直接调用服务方法
        this.delayService.cancelOrder(event.getOrderInfo().getId());
        log.info("success to sent Delay Task to RocketMQ for Cancel Order {}", event.getOrderInfo().getId());
    }
}
