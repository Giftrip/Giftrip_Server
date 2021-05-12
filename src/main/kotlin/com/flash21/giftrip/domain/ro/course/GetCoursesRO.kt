package com.flash21.giftrip.domain.ro.course

import com.flash21.giftrip.domain.entity.Course
import org.springframework.data.domain.Page

class GetCoursesRO(val content: List<GetCourseRO>, page: Page<Course>) {
    
    var totalPage: Int = page.totalPages
    var totalElements: Long = page.totalElements
    
}