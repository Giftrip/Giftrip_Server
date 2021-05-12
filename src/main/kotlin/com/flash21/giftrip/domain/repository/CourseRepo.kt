package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepo : JpaRepository<Course, Long> {
    fun findAllByTitleContaining(page: Pageable, title: String): Page<Course>
}