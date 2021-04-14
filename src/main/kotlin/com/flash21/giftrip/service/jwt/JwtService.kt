package com.flash21.giftrip.service.jwt

import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.auth.AuthTokenModel
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.enums.JwtAuth

interface JwtService {
    fun createToken(user: User, authType: JwtAuth): AuthTokenModel
    fun validateToken(token: String): User
    fun refreshToken(refreshToken: String): TokenRO
}