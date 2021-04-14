package com.flash21.giftrip.service.auth

import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.ro.auth.LoginRO
import com.flash21.giftrip.domain.ro.auth.TokenRO

interface AuthService {
    fun login(loginDTO: LoginDTO): LoginRO
    fun refresh(refreshToken: String): TokenRO
}
