package com.flash21.giftrip.service.auth

import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.UserRepo
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.domain.ro.auth.AuthTokenModel
import com.flash21.giftrip.domain.ro.auth.LoginRO
import com.flash21.giftrip.enums.JwtAuth
import com.flash21.giftrip.service.jwt.JwtServiceImpl
import com.flash21.giftrip.service.user.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException


@Service
class AuthServiceImpl: AuthService {

    @Autowired
    private lateinit var userRepo: UserRepo

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

}