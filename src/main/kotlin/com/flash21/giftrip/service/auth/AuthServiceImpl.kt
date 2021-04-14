package com.flash21.giftrip.service.auth

import com.flash21.giftrip.constant.DateConstant
import com.flash21.giftrip.domain.dto.auth.ChangePwDTO
import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.dto.auth.PhoneCheckDTO
import com.flash21.giftrip.domain.dto.auth.RegisterDTO
import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.PhoneAuthRepo
import com.flash21.giftrip.domain.repository.UserRepo
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.domain.ro.auth.AuthTokenModel
import com.flash21.giftrip.domain.ro.auth.LoginRO
import com.flash21.giftrip.enums.JwtAuth
import com.flash21.giftrip.lib.GenerateCode
import com.flash21.giftrip.service.jwt.JwtServiceImpl
import com.flash21.giftrip.service.user.UserServiceImpl
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.util.*


@Service
class AuthServiceImpl: AuthService {

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var phoneAuthRepo: PhoneAuthRepo

    @Autowired
    private lateinit var jwtService: JwtServiceImpl

    @Autowired
    private lateinit var userService: UserServiceImpl

    override fun login(loginDTO: LoginDTO): LoginRO {
        val user: User = userRepo.findByPhoneNumberAndPw(loginDTO.phoneNumber, loginDTO.pw)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "잘못된 정보.")
                }

        val accessToken: AuthTokenModel = jwtService.createToken(user, JwtAuth.ACCESS)
        val refreshToken: AuthTokenModel = jwtService.createToken(user, JwtAuth.REFRESH)

        return LoginRO(userService.getMyInfo(user), TokenRO(accessToken, refreshToken))
    }

    override fun refresh(refreshToken: String): TokenRO {
        return jwtService.refreshToken(refreshToken)
    }

    override fun getAuthCode(phoneNumber: String): PhoneAuth {
        userRepo.findByPhoneNumber(phoneNumber)
                .ifPresent {
                    throw HttpClientErrorException(HttpStatus.CONFLICT, "이미 가입된 전번.")
                }

        val phoneAuth: PhoneAuth = phoneAuthRepo
                .findByPhoneNumber(phoneNumber)
                .orElse(PhoneAuth())

        if (Date().before(phoneAuth.retryAt)) {
            throw HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, "아직 발급 불가.")
        }

        phoneAuth.phoneNumber = phoneNumber

        if (!phoneAuth.reset(GenerateCode.execute())) {
            throw HttpClientErrorException(HttpStatus.FORBIDDEN, "이미 인증됨.")
        }

        phoneAuthRepo.save(phoneAuth)

        return phoneAuth
    }

    override fun checkAuthCode(phoneCheckDTO: PhoneCheckDTO) {
        val phoneAuth: PhoneAuth = phoneAuthRepo
                .findByPhoneNumberAndCode(phoneCheckDTO.phoneNumber, phoneCheckDTO.code)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 정보 불일치.")
                }

        phoneAuth.isCertified = true
        phoneAuth.code = null
        phoneAuthRepo.save(phoneAuth)
    }

    override fun register(registerDTO: RegisterDTO) {
        userRepo.findByPhoneNumber(registerDTO.phoneNumber)
                .ifPresent {
                    throw HttpClientErrorException(HttpStatus.CONFLICT, "이미 가입된 전번.")
                }

        val phoneAuth: PhoneAuth = phoneAuthRepo
                .findByPhoneNumber(registerDTO.phoneNumber)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 정보 없음.")
                }
        if (!phoneAuth.isCertified) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 안됨.")


        val user: User = ModelMapper().map(registerDTO, User::class.java)

        phoneAuthRepo.delete(phoneAuth)
        userRepo.save(user)
    }

    override fun changePw(changePwDTO: ChangePwDTO, user: User) {
        user.pw = changePwDTO.pw
        userRepo.save(user)
    }

}