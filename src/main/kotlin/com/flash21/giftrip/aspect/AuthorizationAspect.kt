//package com.flash21.giftrip.aspect
//
//import com.flash21.giftrip.annotation.AuthorizationApi
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Before
//import org.aspectj.lang.annotation.Pointcut
//import org.springframework.stereotype.Component
//
//@Component
//@Aspect
//open class AuthorizationAspect {
//
//    @AuthorizationApi("")
//    @Pointcut("@annotation(com.flash21.giftrip.annotation.AuthorizationApi)")
//    fun authorizationApi() {}
//
//    @Before("authorizationApi()")
//}