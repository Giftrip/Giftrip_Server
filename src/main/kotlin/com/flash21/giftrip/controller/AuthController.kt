package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.auth.ChangePwByCodeDTO
import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.dto.auth.RefreshDTO
import com.flash21.giftrip.domain.dto.auth.RegisterDTO
import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.ro.auth.PhoneAuthCodeRO
import com.flash21.giftrip.domain.ro.auth.PhonePwAuthCodeRO
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.service.auth.AuthServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Size

@RestController
@RequestMapping("/auth")
@Validated
class AuthController {
    
    @Autowired
    private lateinit var authService: AuthServiceImpl
    
    @PostMapping("/login")
    @ApiOperation(value = "로그인 API")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = TokenRO::class),
        ApiResponse(code = 401, message = "인증 오류.", response = Response::class)
    ])
    fun login(@RequestBody loginDTO: LoginDTO): TokenRO {
        return authService.login(loginDTO)
    }
    
    @PostMapping("/refresh")
    @ApiOperation(value = "토큰갱신 API", notes = "response.data.token.refreshToken 은 nullable 로 refreshToken이 만료 3시간 전이면 자동으로 refreshToken도 같이 갱신")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = TokenRO::class),
        ApiResponse(code = 401, message = "토큰 타입이 Access토큰임.", response = Response::class),
        ApiResponse(code = 404, message = "해당 유저 없음.", response = Response::class)
    ])
    fun refresh(@RequestBody refreshDTO: RefreshDTO): TokenRO {
        return authService.refresh(refreshDTO.refreshToken)
    }
    
    @GetMapping("/getAuthCode")
    @ApiOperation(value = "휴대폰 인증 생성 및 코드 조회")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 409, message = "이미 가입된 전번.", response = Response::class),
        ApiResponse(code = 429, message = "아직 발급 불가.", response = Response::class)
    ])
    fun createAuthCode(@RequestParam(required = true) @Size(min = 1, max = 20) phoneNumber: String): PhoneAuthCodeRO {
        return authService.createAuthCode(phoneNumber)
    }
    
    @PostMapping("/register")
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = TokenRO::class),
        ApiResponse(code = 401, message = "인증 정보 불일치.", response = Response::class),
        ApiResponse(code = 409, message = "이미 가입된 전화번호.", response = Response::class),
        ApiResponse(code = 410, message = "인증 시간 만료", response = Response::class)
    ])
    fun register(@RequestBody registerDTO: RegisterDTO): TokenRO {
        authService.register(registerDTO)
        val loginData = LoginDTO()
        loginData.phoneNumber = registerDTO.phoneNumber
        loginData.pw = registerDTO.pw
        return authService.login(loginData)
    }
    
    @GetMapping("/getPwAuthCode")
    @ApiOperation(value = "비밀번호 변경 휴대폰 인증 생성 및 코드 조회 (비밀번호 찾기)")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = PhoneAuth::class),
        ApiResponse(code = 404, message = "해당 전화번호 유저가 없음.", response = Response::class),
        ApiResponse(code = 429, message = "아직 발급 불가.", response = Response::class)
    ])
    fun getPwAuthCode(@RequestParam(required = true) @Size(min = 1, max = 20) phoneNumber: String): PhonePwAuthCodeRO {
        return authService.createPwAuthCode(phoneNumber)
    }
    
    @PatchMapping("/changePwByCode")
    @ApiOperation(value = "비밀번호 인증 코드로 변경 (비밀번호 찾기)")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 401, message = "인증 정보 불일치.", response = Response::class),
        ApiResponse(code = 404, message = "해당 전화번호 유저가 없음.", response = Response::class),
        ApiResponse(code = 410, message = "인증 시간 만료", response = Response::class)
    ])
    fun changePwByCode(@RequestBody changePwByCodeDTO: ChangePwByCodeDTO): Response {
        authService.changePwByCode(changePwByCodeDTO)
        return Response("변경 성공.")
    }
    
}
