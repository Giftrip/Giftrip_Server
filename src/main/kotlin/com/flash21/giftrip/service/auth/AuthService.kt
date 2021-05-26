package com.flash21.giftrip.service.auth

import com.flash21.giftrip.domain.dto.auth.ChangePwByCodeDTO
import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.dto.auth.RegisterDTO
import com.flash21.giftrip.domain.ro.auth.PhoneAuthCodeRO
import com.flash21.giftrip.domain.ro.auth.PhonePwAuthCodeRO
import com.flash21.giftrip.domain.ro.auth.TokenRO

interface AuthService {
    fun login(loginDTO: LoginDTO): TokenRO
    fun refresh(refreshToken: String): TokenRO
    fun createAuthCode(phoneNumber: String): PhoneAuthCodeRO
    fun register(registerDTO: RegisterDTO)
    fun createPwAuthCode(phoneNumber: String): PhonePwAuthCodeRO
    fun changePwByCode(changePwByCodeDTO: ChangePwByCodeDTO)
}
