package com.flash21.giftrip.annotation

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AuthorizationApi(val value: String, val notes: String = "") {

//    @ApiOperation(value = value, authorizations = [Authorization(value="Bearer Token")])
}