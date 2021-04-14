package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.PhoneAuth
import com.flash21.giftrip.domain.entity.PhonePwAuth
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PhonePwAuthRepo: JpaRepository<PhonePwAuth, Long> {
    fun findByPhoneNumberAndCode(phoneNumber: String, code: String): Optional<PhonePwAuth>
    fun findByPhoneNumber(phoneNumber: String): Optional<PhonePwAuth>
}