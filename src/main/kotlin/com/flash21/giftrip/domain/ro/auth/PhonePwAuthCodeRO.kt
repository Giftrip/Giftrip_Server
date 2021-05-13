package com.flash21.giftrip.domain.ro.auth

import com.flash21.giftrip.domain.entity.PhonePwAuth
import java.util.*

class PhonePwAuthCodeRO(phonePwAuth: PhonePwAuth) {
    val expireAt: Date = phonePwAuth.expireAt
    val retryAt: Date = phonePwAuth.retryAt
}