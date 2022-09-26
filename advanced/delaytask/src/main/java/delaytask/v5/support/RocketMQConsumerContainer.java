package com.geekhalo.delaytask.v5.support;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Consumer 容器，用于对 DefaultMQPushConsumer 的封装
 */
@Data
@Slf4j
public class RocketMQConsumerContainer implements InitializingBean, SmartLifecycle {
    private DefaultMQPushConsumer consumer;
    private boolean running;
    private String consumerGroup;
    private String nameServerAddress;
    private String topic;
    private Object bean;
    private Method method;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void start() {
        if (this.running){
            return;
        }
        try {
            this.consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        this.running = true;
    }

    @Override
    public void stop() {
        this.running = false;
        this.consumer.shutdown();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 构建 DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup(this.consumerGroup);
        consumer.setNamesrvAddr(this.nameServerAddress);

        // 订阅 topic
        consumer.subscribe(topic, "*");
        // 增加拦截器
        consumer.setMessageListener(new DefaultMessageListenerOrderly());
        this.consumer = consumer;
    }

    private class DefaultMessageListenerOrderly implements MessageListenerOrderly {

        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();

                    // 从 Message 中反序列化数据，获得方法调用参数
                    byte[] body = messageExt.getBody();
                    String bodyAsStr = new String(body);
                    Map deserialize = SerializeUtil.deserialize(bodyAsStr, Map.class);
                    Object[] params = new Object[method.getParameterCount()];

                    for (int i = 0; i< method.getParameterCount(); i++){
                        String o = (String)deserialize.get(String.valueOf(i));
                        if (o == null){
                            params[i] = null;
                        }else {
                            params[i] = SerializeUtil.deserialize(o, method.getParameterTypes()[i]);
                        }
                    }

                    // 执行业务方法
                    method.invoke(bean, params);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setSuspendCurrentQueueTimeMillis(1000);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }
}
