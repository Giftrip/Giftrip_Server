package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.notice.GetNoticeRO
import com.flash21.giftrip.domain.ro.notice.GetNoticesRO
import com.flash21.giftrip.lib.ClientUtils
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
            val user: User = ClientUtils.getAdmin(request)
            noticeService.createNotice(handleNoticeDTO, ClientUtils.getIp(request), user)
            return Response("생성 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PatchMapping("/editNotice/{idx}")
    @ApiOperation(value = "공지 수정 (관리자)", authorizations = [Authorization(value="Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 글 없음.", response = Response::class)
    ])
    fun editNotice(@RequestBody handleNoticeDTO: HandleNoticeDTO,
                   @PathVariable idx: Long, request: HttpServletRequest): Response {
        try {
            val user: User = ClientUtils.getAdmin(request)
            noticeService.editNotice(handleNoticeDTO, idx, ClientUtils.getIp(request), user)
            return Response("수정 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @DeleteMapping("/deleteNotice/{idx}")
    @ApiOperation(value = "공지 삭제 (관리자)", authorizations = [Authorization(value="Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 글 없음.", response = Response::class)
    ])
    fun deleteNotice(@PathVariable idx: Long, request: HttpServletRequest): Response {
        try {
            val user: User = ClientUtils.getAdmin(request)
            noticeService.deleteNotice(idx, ClientUtils.getIp(request), user)
            return Response("삭제 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @GetMapping("/getNotices")
    @ApiOperation(value = "공지 목록 조회", authorizations = [Authorization(value="Bearer Token")])
    fun getNotices(@RequestParam(required = true) @Min(1) page: Int,
                   @RequestParam(required = true) @Min(1) size: Int,
                    request: HttpServletRequest): GetNoticesRO {
        try {
            val user: User = ClientUtils.getUser(request)
            return noticeService.getNotices(page, size, user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @GetMapping("/getNotice/{idx}")
    @ApiOperation(value = "공지 조회", authorizations = [Authorization(value="Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = GetNoticeRO::class),
        ApiResponse(code = 404, message = "해당 글 없음.", response = Response::class)
    ])
    fun getNotice(@PathVariable idx: Long, request: HttpServletRequest): GetNoticeRO {
        try {
            val user: User = ClientUtils.getUser(request)
            return noticeService.getNotice(idx, user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

}