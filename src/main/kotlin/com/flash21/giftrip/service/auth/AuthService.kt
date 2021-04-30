package com.flash21.giftrip.service.auth

import com.flash21.giftrip.domain.dto.auth.*
import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.entity.PhonePwAuth
import com.flash21.giftrip.domain.ro.auth.TokenRO

interface AuthService {
    fun login(loginDTO: LoginDTO): TokenRO
    fun refresh(refreshToken: String): TokenRO
    fun getAuthCode(phoneNumber: String): PhoneAuth
    fun register(registerDTO: RegisterDTO)
    fun getPwAuthCode(phoneNumber: String): PhonePwAuth
    fun changePwByCode(changePwByCodeDTO: ChangePwByCodeDTO)
}
