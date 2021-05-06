package com.flash21.giftrip.service.course

import com.flash21.giftrip.domain.dto.course.HandleCourseDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.course.GetCourseRO

interface CourseService {
    fun createCourse(handleCourseDTO: HandleCourseDTO, ip: String, user: User)
    fun editCourse(handleCourseDTO: HandleCourseDTO, idx: Long)
    fun deleteCourse(idx: Long)
    fun getCourses(page: Int, size: Int, user: User): List<GetCourseRO>
    fun getCourse(idx: Long, user: User): GetCourseRO
    fun searchCourses(page: Int, size: Int, query: String, user: User): List<GetCourseRO>
}