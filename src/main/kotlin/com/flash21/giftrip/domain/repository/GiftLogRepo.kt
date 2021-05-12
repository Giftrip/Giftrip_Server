package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Course
import com.flash21.giftrip.domain.entity.GiftLog
import com.flash21.giftrip.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface GiftLogRepo : JpaRepository<GiftLog, Long> {
    fun findByUserAndCourse(user: User, course: Course): GiftLog?
    fun findAllByCourse(course: Course, page: Pageable): Page<GiftLog>
}