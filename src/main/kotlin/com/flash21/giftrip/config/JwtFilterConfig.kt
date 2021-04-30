package com.flash21.giftrip.config

import com.flash21.giftrip.filter.JwtAuthenticationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class JwtFilterConfig(private val handlerExceptionResolver: HandlerExceptionResolver) {

    @Bean
    fun authFilter(): FilterRegistrationBean<JwtAuthenticationFilter> {
        val registrationBean: FilterRegistrationBean<JwtAuthenticationFilter> = FilterRegistrationBean<JwtAuthenticationFilter>()
        registrationBean.filter = JwtAuthenticationFilter(handlerExceptionResolver)
        registrationBean.addUrlPatterns("/auth/changePw")
        registrationBean.addUrlPatterns("/user/*")
        registrationBean.addUrlPatterns("/notice/*")
        registrationBean.order = 2

        return registrationBean
    }
}