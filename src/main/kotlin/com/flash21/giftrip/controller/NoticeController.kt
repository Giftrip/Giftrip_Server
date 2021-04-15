package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.Notice
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.http.ResponseData
import com.flash21.giftrip.domain.ro.notice.GetNoticesRO
import com.flash21.giftrip.lib.GetUserByHeader
import com.flash21.giftrip.service.notice.NoticeServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Min

@RestController
@RequestMapping("/notice")
class NoticeController {

    @Autowired
    private lateinit var noticeService: NoticeServiceImpl

    @PostMapping("/createNotice")
    @ApiOperation(value = "공지 생성 (관리자)", authorizations = [Authorization(value="Bearer Token")])
    fun createNotice(@RequestBody handleNoticeDTO: HandleNoticeDTO,
                     request: HttpServletRequest): Response {
        try {
            GetUserByHeader.getAdmin(request)
            noticeService.createNotice(handleNoticeDTO)
            return Response(HttpStatus.OK, "생성 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PatchMapping("/editNotices/{idx}")
    @ApiOperation(value = "공지 수정 (관리자)", authorizations = [Authorization(value="Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 404, message = "해당 글 없음.")
    ])
    fun getNotice(@PathVariable idx: Long, request: HttpServletRequest): Response {
        try {
            GetUserByHeader.getAdmin(request)
            return Response(HttpStatus.OK, "조회 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @DeleteMapping("/deleteNotices/{idx}")
    @ApiOperation(value = "공지 삭제 (관리자)", authorizations = [Authorization(value="Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 404, message = "해당 글 없음.")
    ])
    fun deleteNotice(@PathVariable idx: Long, request: HttpServletRequest): Response {
        try {
            GetUserByHeader.getAdmin(request)
            return Response(HttpStatus.OK, "삭제 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @GetMapping("/getNotices")
    @ApiOperation(value = "공지 목록 조회")
    fun getNotices(@RequestParam(required = true) @Min(1) page: Int,
                   @RequestParam(required = true) @Min(1) size: Int): ResponseData<GetNoticesRO> {
        try {
            return ResponseData(HttpStatus.OK, "조회 성공.", noticeService.getNotices(page, size))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @GetMapping("/getNotices/{idx}")
    @ApiOperation(value = "공지 조회")
    @ApiResponses(value = [
        ApiResponse(code = 404, message = "해당 글 없음.")
    ])
    fun getNotice(@PathVariable idx: Long): ResponseData<Notice> {
        try {
            return ResponseData(HttpStatus.OK, "조회 성공.", noticeService.getNotice(idx))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

}