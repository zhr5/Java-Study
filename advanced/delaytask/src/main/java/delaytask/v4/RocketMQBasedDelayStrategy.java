package delaytask.v4;


import delaytask.service.OrderInfoCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;



@Service
@Slf4j
public class RocketMQBasedDelayStrategy {
    private static final String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @TransactionalEventListener
    public void onOrderCreated(OrderInfoCreateEvent event){
        // 将数据 发送至 RocketMQ 的延时队列
        Message<String> message = MessageBuilder
                .withPayload(String.valueOf(event.getOrderInfo().getId()))
                .build();
        this.rocketMQTemplate.syncSend("delay-task-topic", message, 200, 2);
        log.info("success to sent Delay Task to RocketMQ for Cancel Order {}", event.getOrderInfo().getId());
    }
}
