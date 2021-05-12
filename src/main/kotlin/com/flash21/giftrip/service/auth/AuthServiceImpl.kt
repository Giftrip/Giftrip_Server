package com.flash21.giftrip.service.auth

import com.flash21.giftrip.domain.dto.auth.ChangePwByCodeDTO
import com.flash21.giftrip.domain.dto.auth.LoginDTO
import com.flash21.giftrip.domain.dto.auth.RegisterDTO
import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.entity.PhonePwAuth
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.PhoneAuthRepo
import com.flash21.giftrip.domain.repository.PhonePwAuthRepo
import com.flash21.giftrip.domain.repository.UserRepo
import com.flash21.giftrip.domain.ro.auth.AuthTokenModel
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.enums.JwtAuth
import com.flash21.giftrip.lib.GenerateCode
import com.flash21.giftrip.service.jwt.JwtServiceImpl
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.util.*


@Service
class AuthServiceImpl : AuthService {
    
    @Autowired
    private lateinit var userRepo: UserRepo
    
    @Autowired
    private lateinit var phoneAuthRepo: PhoneAuthRepo
    
    @Autowired
    private lateinit var phonePwAuthRepo: PhonePwAuthRepo
    
    @Autowired
    private lateinit var jwtService: JwtServiceImpl
    
    override fun login(loginDTO: LoginDTO): TokenRO {
        val user: User = userRepo.findByPhoneNumberAndPw(loginDTO.phoneNumber, loginDTO.pw)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "잘못된 정보.")
                }
        
        if (user.deleted) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "탈퇴한 회원.")
        
        val accessToken: AuthTokenModel = jwtService.createToken(user, JwtAuth.ACCESS)
        val refreshToken: AuthTokenModel = jwtService.createToken(user, JwtAuth.REFRESH)
        
        return TokenRO(accessToken, refreshToken)
    }
    
    override fun refresh(refreshToken: String): TokenRO {
        return jwtService.refreshToken(refreshToken)
    }
    
    override fun getAuthCode(phoneNumber: String): PhoneAuth {
        userRepo.findByPhoneNumber(phoneNumber)
                .ifPresent {
                    throw HttpClientErrorException(HttpStatus.CONFLICT, "이미 가입된 전화번호.")
                }
        
        val phoneAuth: PhoneAuth = phoneAuthRepo
                .findByPhoneNumber(phoneNumber)
                .orElse(PhoneAuth())
        
        if (Date().before(phoneAuth.retryAt)) {
            throw HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, "아직 발급 불가.")
        }
        
        phoneAuth.phoneNumber = phoneNumber
        phoneAuth.reset(GenerateCode.execute())
        
        phoneAuthRepo.save(phoneAuth)
        
        return phoneAuth
    }
    
    override fun register(registerDTO: RegisterDTO) {
        val phoneAuth: PhoneAuth = phoneAuthRepo
                .findByPhoneNumberAndCode(registerDTO.phoneNumber, registerDTO.code)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 정보 불일치.")
                }
        
        userRepo.findByPhoneNumber(registerDTO.phoneNumber)
                .ifPresent {
                    throw HttpClientErrorException(HttpStatus.CONFLICT, "이미 가입된 전화번호.")
                }
        
        val user: User = ModelMapper().map(registerDTO, User::class.java)
        
        phoneAuthRepo.delete(phoneAuth)
        userRepo.save(user)
    }
    
    override fun getPwAuthCode(phoneNumber: String): PhonePwAuth {
        userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 전화번호 유저가 없음.")
                }
        
        val phonePwAuth: PhonePwAuth = phonePwAuthRepo
                .findByPhoneNumber(phoneNumber)
                .orElse(PhonePwAuth())
        
        if (Date().before(phonePwAuth.retryAt)) {
            throw HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, "아직 발급 불가.")
        }
        
        phonePwAuth.phoneNumber = phoneNumber
        phonePwAuth.reset(GenerateCode.execute())
        
        phonePwAuthRepo.save(phonePwAuth)
        
        return phonePwAuth
    }
    
    override fun changePwByCode(changePwByCodeDTO: ChangePwByCodeDTO) {
        val phonePwAuth: PhonePwAuth = phonePwAuthRepo
                .findByPhoneNumberAndCode(changePwByCodeDTO.phoneNumber, changePwByCodeDTO.code)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 정보 불일치.")
                }
        
        val user: User = userRepo.findByPhoneNumber(changePwByCodeDTO.phoneNumber)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 전화번호 유저 없음.")
                }
        
        user.pw = changePwByCodeDTO.pw
        
        phonePwAuthRepo.delete(phonePwAuth)
        userRepo.save(user)
    }
    
}