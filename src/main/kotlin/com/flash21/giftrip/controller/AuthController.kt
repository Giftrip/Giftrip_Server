package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.auth.*
import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.entity.PhonePwAuth
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.auth.LoginRO
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.http.ResponseData
import com.flash21.giftrip.service.auth.AuthServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Size

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authService: AuthServiceImpl

    @PostMapping("/login")
    @ApiOperation(value = "로그인 API")
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
    @ApiOperation(value = "토큰갱신 API", notes = "response.data.token.refreshToken 은 nullable 로 refreshToken이 만료 3시간 전이면 자동으로 refreshToken도 같이 갱신")
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

    @GetMapping("/getAuthCode")
    @ApiOperation(value = "휴대폰 인증 생성 및 코드 조회")
    @ApiResponses(value = [
        ApiResponse(code = 409, message = "이미 가입된 전번."),
        ApiResponse(code = 429, message = "아직 발급 불가.")
    ])
    fun getAuthCode(@RequestParam(required = true) @Size(min = 1, max = 20) phoneNumber: String): ResponseData<PhoneAuth> {
        try {
            return ResponseData(HttpStatus.OK, "조회 및 생성 성공.", authService.getAuthCode(phoneNumber))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "인증 정보 불일치."),
        ApiResponse(code = 409, message = "이미 가입된 전화번호.")
    ])
    fun register(@RequestBody registerDTO: RegisterDTO): Response {
        try {
            authService.register(registerDTO)
            return Response(HttpStatus.OK, "조회 및 생성 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PatchMapping("/changePw")
    @ApiOperation(value = "비밀번호 변경 By Token", authorizations = [Authorization(value="Bearer Token")])
    fun changePw(@RequestBody changePwDTO: ChangePwDTO, request: HttpServletRequest): Response {
        try {
            val user: User = request.getAttribute("user") as User
            authService.changePw(changePwDTO, user)
            return Response(HttpStatus.OK, "변경 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @GetMapping("/getPwAuthCode")
    @ApiOperation(value = "비밀번호 변경 휴대폰 인증 생성 및 코드 조회 (비밀번호 찾기)")
    @ApiResponses(value = [
        ApiResponse(code = 404, message = "해당 전화번호 유저가 없음."),
        ApiResponse(code = 429, message = "아직 발급 불가.")
    ])
    fun getPwAuthCode(@RequestParam(required = true) @Size(min = 1, max = 20) phoneNumber: String): ResponseData<PhonePwAuth> {
        try {
            return ResponseData(HttpStatus.OK, "조회 및 생성 성공.", authService.getPwAuthCode(phoneNumber))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PatchMapping("/changePwByCode")
    @ApiOperation(value = "비밀번호 인증 코드로 변경 (비밀번호 찾기)")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "인증 정보 불일치."),
        ApiResponse(code = 404, message = "해당 전화번호 유저가 없음.")
    ])
    fun changePwByCode(@RequestBody changePwByCodeDTO: ChangePwByCodeDTO): Response {
        try {
            authService.changePwByCode(changePwByCodeDTO)
            return Response(HttpStatus.OK, "변경 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

}