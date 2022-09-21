package com.geekhalo.delaytask.v2;

import com.geekhalo.delaytask.service.OrderInfoCreateEvent;
import com.geekhalo.delaytask.service.OrderInfoService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DelayQueueStrategy implements SmartLifecycle {
    private final DelayQueue<DelayTask> delayTasks = new DelayQueue<>();
    private final Thread thread = new OrderCancelWorker();
    private boolean running;
    @Autowired
    private OrderInfoService orderInfoService;

    @TransactionalEventListener
    public void onOrderCreated(OrderInfoCreateEvent event){
        // 将 订单号 放入延时队列
        this.delayTasks.offer(new DelayTask(event.getOrderInfo().getId(), 10));
        log.info("success to add Delay Task for Cancel Order {}", event.getOrderInfo().getId());
    }

    /**
     * 启动后台线程，消费延时队列中的任务
     */
    @Override
    public void start() {
        if (this.running){
            return;
        }
        this.thread.start();

        this.running = true;
    }

    /**
     * 停止后台线程
     */
    @Override
    public void stop() {
        if (!this.running){
            return;
        }
        this.thread.interrupt();
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }


    /**
     * 延时任务
     */
    @Value
    private static class DelayTask implements Delayed{
        private final Long orderId;
        private final Date runAt;

        private DelayTask(Long orderId, int delayTime) {
            this.orderId = orderId;
            this.runAt = DateUtils.addSeconds(new Date(), delayTime);
        }

        /**
         * 获取剩余时间
         * @param timeUnit
         * @return
         */
        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(getRunAt().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            if (delayed == this) {
                return 0;
            } else {
                long d = this.getDelay(TimeUnit.NANOSECONDS) - delayed.getDelay(TimeUnit.NANOSECONDS);
                return d == 0L ? 0 : (d < 0L ? -1 : 1);
            }
        }
    }

    /**
     * 后台线程，消费延时队列中的消息
     */
    private class OrderCancelWorker extends Thread {
        @Override
        public void run() {
            // 根据中断状态，确定是否退出
            while (!Thread.currentThread().isInterrupted()){
                DelayTask task = null;
                try {
                    // 从队列中获取任务
                    task = delayTasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 取消订单
                if (task != null){
                    orderInfoService.cancel(task.getOrderId());
                    log.info("Success to Run Delay Task, Cancel Order {}", task.getOrderId());
                }
            }
        }
    }
}
