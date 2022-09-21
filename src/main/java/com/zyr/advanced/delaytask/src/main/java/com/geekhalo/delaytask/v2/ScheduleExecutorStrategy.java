package com.geekhalo.delaytask.v2;

import com.geekhalo.delaytask.service.OrderInfoCreateEvent;
import com.geekhalo.delaytask.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ScheduleExecutorStrategy {
    @Autowired
    private OrderInfoService orderInfoService;

    private ScheduledExecutorService scheduledExecutorService;

    public ScheduleExecutorStrategy(){
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("Schedule-Cancel-Thread-%d")
                .daemon(true)
                .build();
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1, basicThreadFactory);
    }

    @TransactionalEventListener
    public void onOrderCreated(OrderInfoCreateEvent event){
        // 添加定时任务
        this.scheduledExecutorService.schedule(new CancelTask(event.getOrderInfo().getId()), 5, TimeUnit.SECONDS);
        log.info("Success to add cancel task for order {}", event.getOrderInfo().getId());
    }

    private class CancelTask implements Runnable{
        private final Long orderId;

        private CancelTask(Long orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
            // 执行订单取消操作
            orderInfoService.cancel(this.orderId);
            log.info("Success to cancel task for order {}", this.orderId);
        }
    }
}
