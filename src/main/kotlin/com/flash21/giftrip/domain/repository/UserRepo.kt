package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<User, Long> {

}