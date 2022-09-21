package com.geekhalo.delaytask.controller;

import com.geekhalo.delaytask.service.OrderInfoService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 生成新的订单，主要用于测试
     */
    @PostMapping("insertTestData")
    public void createTestOrder(){
        Date date = DateUtils.addMinutes(new Date(), -30);
        date = DateUtils.addSeconds(date, 10);
        this.orderInfoService.create(date);
    }
}
