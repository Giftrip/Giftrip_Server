package com.flash21.giftrip.config

import com.google.common.collect.Lists
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.awt.print.Pageable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*


@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration::class)
class SwaggerConfig {

    final val AUTHORIZATION_HEADER = "Authorization"
    final val DEFAULT_INCLUDE_PATTERN = ".*"

    @Bean
    fun swaggerSpringfoxDocket(): Docket? {
        val contact = Contact(
                "Contact This",
                "http://www.flash21.com/main/main.html",
                "chu799@flash21.com")
        val apiInfo = ApiInfo(
                "Giftrip API",
                "Giftrip API Docs",
                "0.0.1",
                "https://github.com/Giftrip/Giftrip_Server/blob/main/LICENSE",
                contact,
                "GNU",
                "https://github.com/Giftrip/Giftrip_Server/blob/main/LICENSE",
                arrayListOf())
        var docket = Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .pathMapping("/")
                .apiInfo(ApiInfo.DEFAULT)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity::class.java)
                .ignoredParameterTypes(Pageable::class.java)
                .ignoredParameterTypes(java.sql.Date::class.java)
                .directModelSubstitute(LocalDate::class.java, java.sql.Date::class.java)
                .directModelSubstitute(ZonedDateTime::class.java, Date::class.java)
                .directModelSubstitute(LocalDateTime::class.java, Date::class.java)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false)
        docket = docket.select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build()
        return docket
    }


    private fun apiKey(): ApiKey? {
        return ApiKey("Bearer Token", AUTHORIZATION_HEADER, "header")
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build()
    }

    fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
                SecurityReference("Bearer Token", authorizationScopes))
    }
}