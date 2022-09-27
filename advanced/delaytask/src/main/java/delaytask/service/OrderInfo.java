package delaytask.service;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "order_info")
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderInfoStatus orderStatus;

    @Column(name = "create_time")
    private Date createTime = new Date();

    /**
     * 取消订单
     */
    public void cancel() {
        setOrderStatus(OrderInfoStatus.CANCELLED);
    }

    /**
     * 创建订单
     * @param createDate
     * @return
     */
    public static OrderInfo create(Date createDate){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateTime(createDate);
        orderInfo.setOrderStatus(OrderInfoStatus.CREATED);
        return orderInfo;
    }
}
