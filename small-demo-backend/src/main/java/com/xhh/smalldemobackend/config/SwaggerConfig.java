package com.xhh.smalldemobackend.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * /@Api：用于修饰Controller类，生成Controller相关文档信息
     * /@ApiOperation：用于修饰Controller类中的方法，生成接口方法相关文档信息
     * /@ApiParam：用于修饰接口中的参数，生成接口参数相关文档信息
     * /@ApiModelProperty：用于修饰实体类的属性，当实体类是请求参数或返回结果时，直接生成相关文档信息
     */
    
    @Bean
    public Docket api(){
        // swagger版本信息
        return new Docket(DocumentationType.OAS_30)
                // 文档基本信息配置
                .apiInfo(apiInfo())
                // 是否开启swagger，默认开启
                .enable(true)
                // 配置扫描接口
                .select()
                // 配置需要扫描的接口
                // 为有@ApiOperation注解的方法生成API文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //为当前包下controller生成API文档
                //.apis(RequestHandlerSelectors.basePackage("com.xhh.smalldemo.controller"))
                //为有@Api注解的Controller生成API文档
//              .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 过滤请求
                .paths(PathSelectors.any())
                .build();
    }
    
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("接口文档")
                .contact(new Contact("xhh","www.baidu.com","xhh19990210@gmail.com"))
                .build();
    }
}
