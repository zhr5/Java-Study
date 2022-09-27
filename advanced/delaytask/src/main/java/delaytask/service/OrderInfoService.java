package delaytask.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderInfoService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private OrderInfoRepository orderInfoRepository;


    /**
     * 生单接口 <br />
     * 1. 创建订单，保存至数据库
     * 2. 发布领域事件，触发后续处理
     * @param createDate
     */
    @Transactional(readOnly = false)
    public void create(Date createDate){
        OrderInfo orderInfo = OrderInfo.create(createDate);
        this.orderInfoRepository.save(orderInfo);
        eventPublisher.publishEvent(new OrderInfoCreateEvent(orderInfo));
    }

    /**
     * 取消订单
     * @param orderId
     */
    @Transactional(readOnly = false)
    public void cancel(Long orderId){
        Optional<OrderInfo> orderInfoOpt = this.orderInfoRepository.findById(orderId);
        if (orderInfoOpt.isPresent()){
            OrderInfo orderInfo = orderInfoOpt.get();
            orderInfo.cancel();
            this.orderInfoRepository.save(orderInfo);
            log.info("success to cancel order {}", orderId);
        }else {
            log.info("failed to find order {}", orderId);
        }
    }

    /**
     * 查找超时未支付的订单
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrderInfo> findOvertimeNotPaidOrders(Date deadLine){
        return this.orderInfoRepository.getByOrderStatusAndCreateTimeLessThan(OrderInfoStatus.CREATED, deadLine);
    }
}
