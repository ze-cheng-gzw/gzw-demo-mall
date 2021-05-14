package com.demo.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.esVo.SkuInfo;
import com.demo.interfaceService.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "v1", tags = "es查询")
@RequestMapping("/api/v1")
public class SearchController {

    // dubbo用@Reference 调用服务
    @Reference
    private SearchService searchService;

    @GetMapping("/es/test")
    @ApiOperation(value = "测试", notes = "测试")
    public List<SkuInfo> test() {

        List<SkuInfo> skuInfoList = searchService.search();

        return skuInfoList;
    }
}
