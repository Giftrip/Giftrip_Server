package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Course
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepo: JpaRepository<Course, Long> {
    fun findAllByTitleContaining(page: PageRequest, title: String): List<Course>
}