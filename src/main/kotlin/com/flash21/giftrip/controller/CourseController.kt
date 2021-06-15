package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.course.HandleCourseDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.course.GetCourseInfoRO
import com.flash21.giftrip.domain.ro.course.GetCoursesRO
import com.flash21.giftrip.domain.ro.course.GetRecentlyCompleteListRO
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.notice.GetNoticeRO
import com.flash21.giftrip.lib.ClientUtils
import com.flash21.giftrip.service.course.CourseServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Min

@RestController
@RequestMapping("/course")
@Validated
class CourseController {
    
    @Autowired
    private lateinit var courseService: CourseServiceImpl
    
    @PostMapping("/createCourse")
    @ApiOperation(value = "코스 생성 (관리자)", authorizations = [Authorization("Bearer Token")])
    fun createCourse(@RequestBody handleCourseDTO: HandleCourseDTO,
                     request: HttpServletRequest): Response {
        val user: User = ClientUtils.getAdmin(request)
        courseService.createCourse(handleCourseDTO, ClientUtils.getIp(request), user)
        return Response("생성 성공.")
    }
    
    @PatchMapping("/editCourse/{idx}")
    @ApiOperation(value = "코스 수정 (관리자)", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 코스 없음.", response = Response::class)
    ])
    fun editNotice(@RequestBody handleCourseDTO: HandleCourseDTO,
                   @PathVariable idx: Long, request: HttpServletRequest): Response {
        val user: User = ClientUtils.getAdmin(request)
        courseService.editCourse(handleCourseDTO, idx, ClientUtils.getIp(request), user)
        return Response("수정 성공.")
    }
    
    @DeleteMapping("/deleteCourse/{idx}")
    @ApiOperation(value = "코스 삭제 (관리자)", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 코스 없음.", response = Response::class)
    ])
    fun deleteNotice(@PathVariable idx: Long, request: HttpServletRequest): Response {
        val user: User = ClientUtils.getAdmin(request)
        courseService.deleteCourse(idx, ClientUtils.getIp(request), user)
        return Response("삭제 성공.")
    }
    
    @GetMapping("/getCourse/{idx}")
    @ApiOperation(value = "코스 조회", authorizations = [Authorization("Bearer Token")], notes = "completedAt은 미완료시 null로 줌.")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = GetCourseInfoRO::class),
        ApiResponse(code = 404, message = "해당 코스 없음.", response = Response::class)
    ])
    fun getCourse(@PathVariable idx: Long, request: HttpServletRequest): GetCourseInfoRO {
        val user: User = ClientUtils.getUser(request)
        return courseService.getCourse(idx, user)
    }
    
    @GetMapping("/getCourses")
    @ApiOperation(value = "코스 목록 조회", authorizations = [Authorization("Bearer Token")], notes = "completedAt은 미완료시 null로 줌.")
    fun getCourses(@RequestParam(required = true) @Min(1) page: Int,
                   @RequestParam(required = true) @Min(1) size: Int,
                   request: HttpServletRequest): GetCoursesRO {
        val user: User = ClientUtils.getUser(request)
        return courseService.getCourses(page, size, user)
    }
    
    @GetMapping("/searchCourses")
    @ApiOperation(value = "코스 목록 검색", authorizations = [Authorization("Bearer Token")], notes = "completedAt은 미완료시 null로 줌.")
    fun searchCourses(@RequestParam(required = true) @Min(1) page: Int,
                      @RequestParam(required = true) @Min(1) size: Int,
                      @RequestParam(value = "query", required = true) query: String, request: HttpServletRequest): GetCoursesRO {
        val user: User = ClientUtils.getUser(request)
        return courseService.searchCourses(page, size, query, user)
    }
    
    @GetMapping("/getRecentlyComplete/{idx}")
    @ApiOperation(value = "최근 완주자 조회", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = GetNoticeRO::class),
        ApiResponse(code = 404, message = "해당 코스 없음.", response = Response::class)
    ])
    fun getRecentlyComplete(@PathVariable idx: Long, request: HttpServletRequest): GetRecentlyCompleteListRO {
        val user: User = ClientUtils.getUser(request)
        return GetRecentlyCompleteListRO(courseService.getRecentlyComplete(idx, user))
    }
    
}
