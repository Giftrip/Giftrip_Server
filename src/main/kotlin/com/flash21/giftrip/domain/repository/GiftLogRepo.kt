package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Course
import com.flash21.giftrip.domain.entity.GiftLog
import com.flash21.giftrip.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface GiftLogRepo: JpaRepository<GiftLog, Long> {
    fun findByUserAndCourse(user: User, course: Course): GiftLog?
}