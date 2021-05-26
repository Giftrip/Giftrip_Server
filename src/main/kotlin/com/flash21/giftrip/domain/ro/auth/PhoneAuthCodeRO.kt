package com.flash21.giftrip.domain.ro.auth

import com.flash21.giftrip.domain.entity.PhoneAuth
import java.util.*

class PhoneAuthCodeRO(phoneAuth: PhoneAuth) {
    val expireAt: Date = phoneAuth.expireAt
    val retryAt: Date = phoneAuth.retryAt
}