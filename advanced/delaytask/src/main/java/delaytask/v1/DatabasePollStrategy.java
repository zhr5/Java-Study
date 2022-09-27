package delaytask.v1;


import delaytask.service.OrderInfo;
import delaytask.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DatabasePollStrategy {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 每隔 1S 运行一次 <br/>
     * 1. 从 DB 中查询过期未支付订单（状态为 CREATED，创建时间小于 deadLintDate）
     * 2. 依次执行 取消订单 操作
     */
    @Scheduled(fixedDelay = 1 * 1000)
    public void poll(){
        Date now = new Date();
        Date overtime = DateUtils.addMinutes(now, -30);
        List<OrderInfo> overtimeNotPaidOrders = orderInfoService.findOvertimeNotPaidOrders(overtime);
        log.info("load overtime Not paid orders {}", overtimeNotPaidOrders);
        overtimeNotPaidOrders.forEach(orderInfo -> this.orderInfoService.cancel(orderInfo.getId()));
    }
}
