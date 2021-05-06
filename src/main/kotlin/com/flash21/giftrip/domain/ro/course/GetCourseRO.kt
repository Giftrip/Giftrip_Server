package com.flash21.giftrip.domain.ro.course

import com.flash21.giftrip.domain.entity.Course
import com.flash21.giftrip.domain.entity.GiftLog
import java.util.*

class GetCourseRO(course: Course, giftLog: GiftLog?) {

    val idx: Long? = course.idx
    val title: String = course.title
    val description: String = course.description
    val thumbnail: String = course.thumbnail
    val location: String = course.location
    val createdAt: Date = course.createdAt
    val updatedAt: Date = course.updatedAt
    val completed: Boolean = giftLog != null
    val completedAt: Date? = giftLog?.endAt

}