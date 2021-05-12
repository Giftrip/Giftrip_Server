package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.PhoneAuth
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PhoneAuthRepo : JpaRepository<PhoneAuth, Long> {
    fun findByPhoneNumberAndCode(phoneNumber: String, code: String): Optional<PhoneAuth>
    fun findByPhoneNumber(phoneNumber: String): Optional<PhoneAuth>
}
