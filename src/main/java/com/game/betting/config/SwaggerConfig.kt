package com.game.betting.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@EnableSwagger2
@Configuration
open class SwaggerConfig {
    @Bean
    open fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.game.betting.controller"))
            .paths(PathSelectors.any())
            .build().apiInfo(apiInfoMetaData())
    }

    private fun apiInfoMetaData(): ApiInfo {
        return ApiInfoBuilder().title("API Documentation")
            .description("Test Project for a simple odds-based game ")
            .contact(Contact("Jessica Bianco", "", "bianco.jess@gmail.com"))
            .version("1.0.0")
            .build()
    }
}