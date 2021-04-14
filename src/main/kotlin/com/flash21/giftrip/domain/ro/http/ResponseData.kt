package com.flash21.giftrip.domain.ro.http

import org.springframework.http.HttpStatus

class ResponseData<T> (status: HttpStatus, message: String, val data: T)
    : Response(status, message) {
}