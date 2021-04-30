package com.demo.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1", tags = "订单入口")
@RequestMapping("/api/v1")
public class OrderController {

    @GetMapping("/order/test")
    @ApiOperation(value = "测试", notes = "测试")
    public String test() {

        return "测试";
    }
}
