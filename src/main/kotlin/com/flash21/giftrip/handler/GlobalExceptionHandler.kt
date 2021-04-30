package com.flash21.giftrip.handler

import com.flash21.giftrip.domain.ro.http.Response
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.multipart.MultipartException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException::class)
    protected fun handleHttpClientErrorException(e: HttpClientErrorException): ResponseEntity<Response> {
        val data = Response(e.statusText)
        return ResponseEntity<Response>(data, e.statusCode)
    }

    @ExceptionHandler(HttpServerErrorException::class)
    protected fun handleHttpServerErrorException(e: HttpServerErrorException): ResponseEntity<Response> {
        val data = Response(e.statusText)
        return ResponseEntity<Response>(data, e.statusCode)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    protected fun handleHttpMediaTypeNotSupportedException(e: HttpMediaTypeNotSupportedException): ResponseEntity<Response> {
        val data = Response("지원되지 않는 미디어 타입.")
        return ResponseEntity<Response>(data, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<Response> {
        val data = Response("검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    protected fun handleWebExchangeBindException(e: WebExchangeBindException): ResponseEntity<Response> {
        val data = Response("검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Response> {
        val data = Response("검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(javax.naming.SizeLimitExceededException::class)
    protected fun handleSizeLimitExceededException(e: SizeLimitExceededException): ResponseEntity<Response> {
        val data = Response("너무 크기가 큼.")
        return ResponseEntity(data, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MultipartException::class)
    protected fun handleMultipartException(e: MultipartException): ResponseEntity<Response> {
        val data = Response("검증 오류.")
        return ResponseEntity<Response>(data, HttpStatus.BAD_REQUEST)
    }

}