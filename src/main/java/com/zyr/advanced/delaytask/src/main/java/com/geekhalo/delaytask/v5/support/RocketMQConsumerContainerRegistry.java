package com.geekhalo.delaytask.v5.support;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于 BeanPostProcessor#postProcessAfterInitialization 对每个 bean 进行处理
 * 扫描 bean 中被 @RocketMQBasedDelay 注解的方法，并将方法封装成 RocketMQConsumerContainer，
 * 以启动 DefaultMQPushConsumer
 */
public class RocketMQConsumerContainerRegistry implements BeanPostProcessor {
    private final AtomicInteger id = new AtomicInteger(1);
    @Autowired
    private GenericApplicationContext applicationContext;
    @Value("${rocketmq.name-server}")
    private String nameServerAddress;

    /**
     * 对每个 bean 依次进行处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 1. 获取 @RocketMQBasedDelay 注解方法
        Class targetCls = AopUtils.getTargetClass(bean);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, RocketMQBasedDelay.class);

        // 2. 为每个 @RocketMQBasedDelay 注解方法 注册 RocketMQConsumerContainer
        for(Method method : methodsListWithAnnotation){
            String containerBeanName = targetCls.getName() + "#" + method.getName() + id.getAndIncrement();
            RocketMQBasedDelay annotation = method.getAnnotation(RocketMQBasedDelay.class);
            applicationContext.registerBean(containerBeanName, RocketMQConsumerContainer.class, () -> createContainer(bean, method, annotation));
        }

        return bean;
    }

    /**
     * 构建 RocketMQConsumerContainer
     * @param proxy
     * @param method
     * @param annotation
     * @return
     */
    private  RocketMQConsumerContainer createContainer(Object proxy, Method method, RocketMQBasedDelay annotation) {
        Object bean = AopProxyUtils.getSingletonTarget(proxy);
        RocketMQConsumerContainer container = new RocketMQConsumerContainer();
        container.setBean(bean);
        container.setMethod(method);
        container.setConsumerGroup(annotation.consumerGroup());
        container.setNameServerAddress(nameServerAddress);
        container.setTopic(annotation.topic());
        return container;
    }
}
