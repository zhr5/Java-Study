package com.geekhalo.delaytask.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    List<OrderInfo> getByOrderStatusAndCreateTimeLessThan(OrderInfoStatus created, Date overtime);
}
