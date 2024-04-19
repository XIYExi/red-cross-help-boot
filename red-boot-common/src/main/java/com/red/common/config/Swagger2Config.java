package com.red.common.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .protocols(Sets.newHashSet("http", "https"))
                .apiInfo(webApiInfo())
                .select()
                /* .paths(Predicates.not(PathSelectors.regex("/admin/.*")))*/
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    public ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("Envelope-cli Api文档")
                .description("答应我，看不懂先百度，好吗？")
                .version("1.0")
                .contact(new Contact("xiye","","2151885318@qq.com"))
                .build();
    }
}

