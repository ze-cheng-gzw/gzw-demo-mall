package com.demo.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.entity.Member;
import com.demo.interfaceService.MemberService;
import com.demo.vo.FindVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1", tags = "订单入口")
@RequestMapping("/api/v1")
public class OrderController {

    // dubbo用@Reference 调用服务
    @Reference
    private MemberService memberService;

    @GetMapping("/order/test")
    @ApiOperation(value = "测试", notes = "测试")
    public FindVO test() {

        FindVO findVO = new FindVO();
        findVO.setId(1L);
        findVO.setName("桂志伟");
        findVO.setAge(23);

        return findVO;
    }

    @GetMapping("/order/getMemberById")
    @ApiOperation(value = "测试dubbo之间的链接", notes = "测试")
    public Member getMemberById() {

        return memberService.findMemberById();
    }

    @GetMapping("/order/distributedLock_redisSon")
    @ApiOperation(value = "使用redisSon来使用分布式锁", notes = "测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "用户Id", required = true, paramType = "query", dataType = "Long")
    })
    public Member distributedLock_redisSon(Long memberId) {

        return memberService.distributedLock_redisSon(memberId);
    }
}
