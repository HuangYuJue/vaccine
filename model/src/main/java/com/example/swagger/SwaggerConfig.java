package com.example.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo adminApi(){
        return new ApiInfoBuilder()
                .title("后台管理系统---api文档")
                .description("后台管理系统---接口描述")
                .version("1.0")
                .contact(new Contact("黄钰珏","http://baidu.com","2724074124@qq.com"))
                .build();
    }
    @Bean
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApi())
                .select()
                .paths(PathSelectors.regex("/admin/.*"))
                .build();
    }
    private ApiInfo userApi(){
        return new ApiInfoBuilder()
                .title("疫苗预约网站---api文档")
                .description("疫苗预约网站---接口描述")
                .version("1.0")
                .contact(new Contact("黄钰珏","http://baidu.com","2724074124@qq.com"))
                .build();
    }
    @Bean
    public Docket userApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("userApi")
                .apiInfo(userApi())
                .select()
                .paths(PathSelectors.regex("/api/users/.*"))
                .build();
    }
}
