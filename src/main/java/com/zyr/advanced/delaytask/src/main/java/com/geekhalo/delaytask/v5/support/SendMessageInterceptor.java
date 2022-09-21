package com.geekhalo.delaytask.v5.support;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 拦截方法调用，并将请求封装成 Message 发送至 RocketMQ 的 Topic
 */
@Slf4j
public class SendMessageInterceptor implements MethodInterceptor {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();

        // 1. 获取 方法上的注解信息
        RocketMQBasedDelay rocketMQBasedDelay = method.getAnnotation(RocketMQBasedDelay.class);

        // 2. 将请求参数 转换为 MQ
        Object[] arguments = methodInvocation.getArguments();
        String argData = serialize(arguments);
        Message<String> message = MessageBuilder
                .withPayload(argData)
                .build();

        // 3. 发送 MQ
        this.rocketMQTemplate.syncSend(rocketMQBasedDelay.topic(), message , 200, rocketMQBasedDelay.delayLevel());
        log.info("success to sent Delay Task to RocketMQ for {}", Arrays.toString(arguments));
        return null;
    }

    private String serialize(Object[] arguments) {
        Map<String, String> result = Maps.newHashMapWithExpectedSize(arguments.length);
        for (int i = 0; i < arguments.length; i++){
             result.put(String.valueOf(i), SerializeUtil.serialize(arguments[i]));
        }
        return SerializeUtil.serialize(result);
    }

}
