package delaytask.v5.support;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RocketMQBasedDelayConfiguration {

    /**
     * 声明 RocketMQConsumerContainerRegistry，扫描 RocketMQBasedDelay 方法，
     * 创建 DefaultMQPushConsumer 并完成注册
     * @return
     */
    @Bean
    public RocketMQConsumerContainerRegistry rocketMQConsumerContainerRegistry(){
        return new RocketMQConsumerContainerRegistry();
    }

    /**
     * 声明 AOP 拦截器
     * 在调用 @RocketMQBasedDelay 注解方法时，自动拦截，将请求发送至 RocketMQ
     * @return
     */
    @Bean
    public SendMessageInterceptor messageSendInterceptor(){
        return new SendMessageInterceptor();
    }

    /**
     * 对 @RocketMQBasedDelay 标注方法进行拦截
     * @param sendMessageInterceptor
     * @return
     */
    @Bean
    public PointcutAdvisor pointcutAdvisor(@Autowired SendMessageInterceptor sendMessageInterceptor){
        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, RocketMQBasedDelay.class), sendMessageInterceptor);
    }

}
