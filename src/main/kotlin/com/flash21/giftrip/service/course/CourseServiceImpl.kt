package com.flash21.giftrip.service.course

import com.flash21.giftrip.domain.dto.course.HandleCourseDTO
import com.flash21.giftrip.domain.entity.Course
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.CourseRepo
import com.flash21.giftrip.domain.repository.GiftLogRepo
import com.flash21.giftrip.domain.ro.course.GetCourseRO
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class CourseServiceImpl : CourseService {
    
    @Autowired
    private lateinit var courseRepo: CourseRepo
    
    @Autowired
    private lateinit var giftLogRepo: GiftLogRepo
    
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    
    override fun createCourse(handleCourseDTO: HandleCourseDTO, ip: String, user: User) {
        val course: Course = Course()
        
        course.title = handleCourseDTO.title
        course.description = handleCourseDTO.description
        course.city = handleCourseDTO.city
        course.thumbnail = handleCourseDTO.thumbnail
        logger.info("$ip USER ${user.idx} - CREATE\n${Gson().toJson(course)}")
        
        courseRepo.save(course)
    }
    
    override fun editCourse(handleCourseDTO: HandleCourseDTO, idx: Long, ip: String, user: User) {
        val course: Course = courseRepo.findById(idx)
                .orElseThrow { throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 코스 없음.") }
        val prevCourse: String = Gson().toJson(course)
        course.title = handleCourseDTO.title
        course.description = handleCourseDTO.description
        course.city = handleCourseDTO.city
        course.thumbnail = handleCourseDTO.thumbnail
        logger.info("$ip USER ${user.idx} - PATCH\n$prevCourse\n${Gson().toJson(course)}")
        
        courseRepo.save(course)
    }
    
    override fun deleteCourse(idx: Long, ip: String, user: User) {
        val course: Course = courseRepo.findById(idx)
                .orElseThrow { throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 코스 없음.") }
        
        logger.info("$ip USER ${user.idx} - DELETE\n${Gson().toJson(course)}")
        courseRepo.delete(course)
    }
    
    override fun getCourses(page: Int, size: Int, user: User): List<GetCourseRO> {
        if (page < 1 || size < 1) throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
        val item = PageRequest.of(page - 1, size, Sort.by("createdAt").ascending())
        
        val result: MutableList<GetCourseRO> = mutableListOf()
        courseRepo.findAll(item).map {
            result.add(GetCourseRO(it, giftLogRepo.findByUserAndCourse(user, it)))
        }
        
        return result
    }
    
    override fun getCourse(idx: Long, user: User): GetCourseRO {
        val course: Course = courseRepo.findById(idx)
                .orElseThrow { throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 코스 없음.") }
        
        return GetCourseRO(course, giftLogRepo.findByUserAndCourse(user, course))
    }
    
    override fun searchCourses(page: Int, size: Int, query: String, user: User): List<GetCourseRO> {
        if (page < 1 || size < 1) throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
        val item = PageRequest.of(page - 1, size, Sort.by("createdAt").ascending())
        
        val result: MutableList<GetCourseRO> = mutableListOf()
        courseRepo.findAllByTitleContaining(item, query).map {
            result.add(GetCourseRO(it, giftLogRepo.findByUserAndCourse(user, it)))
        }
        
        return result
    }
    
}