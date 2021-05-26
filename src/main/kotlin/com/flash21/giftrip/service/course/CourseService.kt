package com.flash21.giftrip.service.course

import com.flash21.giftrip.domain.dto.course.HandleCourseDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.course.GetCourseInfoRO
import com.flash21.giftrip.domain.ro.course.GetCoursesRO
import com.flash21.giftrip.domain.ro.course.GetRecentlyCompleteRO

interface CourseService {
    fun createCourse(handleCourseDTO: HandleCourseDTO, ip: String, user: User)
    fun editCourse(handleCourseDTO: HandleCourseDTO, idx: Long, ip: String, user: User)
    fun deleteCourse(idx: Long, ip: String, user: User)
    fun getCourses(page: Int, size: Int, user: User): GetCoursesRO
    fun getCourse(idx: Long, user: User): GetCourseInfoRO
    fun searchCourses(page: Int, size: Int, query: String, user: User): GetCoursesRO
    fun getRecentlyComplete(idx: Long, user: User): List<GetRecentlyCompleteRO>
}