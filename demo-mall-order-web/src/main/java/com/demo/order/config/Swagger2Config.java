package com.demo.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {

        ParameterBuilder tokenParam = new ParameterBuilder();
        ParameterBuilder memberType = new ParameterBuilder();
        List<Parameter> swaggerParams = new ArrayList<Parameter>();
        memberType.name("memberType").description("用户类型")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        tokenParam.name("token").description("用户认证信息")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        swaggerParams.add(memberType.build());
        swaggerParams.add(tokenParam.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .ignoredParameterTypes(HrMember.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.order.controller"))// 修改为自己的 controller 包路径
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(swaggerParams);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("swagger文档 by han")
                .version("2.0")
                .build();
    }
}