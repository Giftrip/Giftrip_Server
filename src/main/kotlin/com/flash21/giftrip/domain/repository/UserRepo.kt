package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo: JpaRepository<User, Long> {
    fun findByPhoneNumberAndPw(phoneNumber: String, pw: String): Optional<User>
}