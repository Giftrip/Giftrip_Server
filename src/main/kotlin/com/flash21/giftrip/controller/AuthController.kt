package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.dto.auth.RefreshDTO
import com.flash21.giftrip.domain.ro.auth.LoginRO
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.domain.ro.http.ResponseData
import com.flash21.giftrip.service.auth.AuthServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authService: AuthServiceImpl

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseData<LoginRO> {
        try {
            return ResponseData(HttpStatus.OK, "로그인 성공.", authService.login(loginDTO))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshDTO: RefreshDTO): ResponseData<TokenRO> {
        try {
            return ResponseData(HttpStatus.OK, "갱신 성공.", authService.refresh(refreshDTO.refreshToken))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

}