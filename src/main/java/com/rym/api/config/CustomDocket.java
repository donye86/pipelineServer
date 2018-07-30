package com.rym.api.config;

import com.google.common.base.Predicate;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

public class CustomDocket extends Docket {
    public CustomDocket(boolean enableSwagger, String host) {
        super(DocumentationType.SWAGGER_2);
        Predicate<String> selector = enableSwagger ? PathSelectors.ant("/dcxy/api") : PathSelectors.none();
        this.apiInfo(info())
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .enableUrlTemplating(false)
                .host(host)
                .protocols(protocols())
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rym"))
                .paths(selector)
                .build();
    }

    private Set<String> protocols() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");
        protocols.add("https");
        return protocols;
    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
                .title("多彩校园APP第三方洗衣服务后台接口")
                .description("接口描述文档")
                .version("1.0")
                .contact(new Contact("石广路", "", "shiguanglu@168cad.top"))
                .build();
    }
}
