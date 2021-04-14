package com.flash21.giftrip.domain.ro.http

import org.springframework.http.HttpStatus

open class Response(status: HttpStatus, val message: String) {
    val status: Int = status.value()
}