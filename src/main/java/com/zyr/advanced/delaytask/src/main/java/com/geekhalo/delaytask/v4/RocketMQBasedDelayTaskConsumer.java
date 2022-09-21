package com.geekhalo.delaytask.v4;

import com.geekhalo.delaytask.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(topic = "delay-task-topic", consumerGroup = "delay-task-consumer-group")
public class RocketMQBasedDelayTaskConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 接收消息回调，执行取消订单操作
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        String idAsStr = new String(body);
        orderInfoService.cancel(Long.valueOf(idAsStr));
    }
}
