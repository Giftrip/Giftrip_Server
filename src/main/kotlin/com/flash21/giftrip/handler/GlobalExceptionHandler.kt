package com.flash21.giftrip.handler

import com.flash21.giftrip.domain.ro.http.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException::class)
    protected fun handleHttpClientErrorException(e: HttpClientErrorException): ResponseEntity<Response> {
        val data = Response(e.statusCode, e.statusText)
        return ResponseEntity<Response>(data, e.statusCode)
    }

    @ExceptionHandler(HttpServerErrorException::class)
    protected fun handleHttpServerErrorException(e: HttpServerErrorException): ResponseEntity<Response> {
        val data = Response(e.statusCode, e.statusText)
        return ResponseEntity<Response>(data, e.statusCode)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    protected fun handleHttpMediaTypeNotSupportedException(e: HttpMediaTypeNotSupportedException): ResponseEntity<Response> {
        val data = Response(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입.")
        return ResponseEntity<Response>(data, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<Response> {
        val data = Response(HttpStatus.BAD_REQUEST, "검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    protected fun handleWebExchangeBindException(e: WebExchangeBindException): ResponseEntity<Response> {
        val data = Response(HttpStatus.BAD_REQUEST, "검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Response> {
        val data = Response(HttpStatus.BAD_REQUEST, "검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }
}