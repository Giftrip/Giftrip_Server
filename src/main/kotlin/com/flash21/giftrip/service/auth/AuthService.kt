package com.flash21.giftrip.service.auth

import com.flash21.giftrip.domain.dto.auth.ChangePwDTO
import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.dto.auth.PhoneCheckDTO
import com.flash21.giftrip.domain.dto.auth.RegisterDTO
import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.auth.LoginRO
import com.flash21.giftrip.domain.ro.auth.TokenRO

interface AuthService {
    fun login(loginDTO: LoginDTO): LoginRO
    fun refresh(refreshToken: String): TokenRO
    fun getAuthCode(phoneNumber: String): PhoneAuth
    fun checkAuthCode(phoneCheckDTO: PhoneCheckDTO)
    fun register(registerDTO: RegisterDTO)
    fun changePw(changePwDTO: ChangePwDTO, user: User)
}
