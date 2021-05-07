package com.demo.order.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据收集配置，主要作用在于Spring启动时自动加载一个ExecutorService对象.
 * @author Bruce
 * @date 2017/2/22
 *
 * update by Cliff at 2027/11/03
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(6);
    }
}
