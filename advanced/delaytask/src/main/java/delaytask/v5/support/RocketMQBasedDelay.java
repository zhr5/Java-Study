package com.geekhalo.delaytask.v5.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RocketMQBasedDelay {
    /**
     * RocketMQ topic
     * @return
     */
    String topic();

    /**
     * 延时级别
     * @return
     */
    int delayLevel();

    /**
     * 消费者组信息
     * @return
     */
    String consumerGroup();
}
