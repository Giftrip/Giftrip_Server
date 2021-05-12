package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.spot.CompleteQuizDTO
import com.flash21.giftrip.domain.dto.spot.HandleSpotDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.spot.CompleteQuizRO
import com.flash21.giftrip.domain.ro.spot.GetQuizRO
import com.flash21.giftrip.domain.ro.spot.GetSpotRO
import com.flash21.giftrip.domain.ro.spot.GetSpotsRO
import com.flash21.giftrip.lib.ClientUtils
import com.flash21.giftrip.service.spot.SpotServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Min

@RestController
@RequestMapping("/spot")
class SpotController {
    
    @Autowired
    private lateinit var spotService: SpotServiceImpl
    
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    
    @PostMapping("/createSpot")
    @ApiOperation(value = "스팟 생성 (관리자)", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 스팟 또는 코스 없음.", response = Response::class)
    ])
    fun createSpot(@RequestBody handleSpotDTO: HandleSpotDTO,
                   request: HttpServletRequest): Response {
        try {
            val user: User = ClientUtils.getAdmin(request)
            spotService.createSpot(handleSpotDTO, ClientUtils.getIp(request), user)
            return Response("생성 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    @PatchMapping("/editSpot/{idx}")
    @ApiOperation(value = "스팟 수정 (관리자)", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 스팟 또는 코스 없음.", response = Response::class)
    ])
    fun editSpot(@RequestBody handleSpotDTO: HandleSpotDTO,
                 @PathVariable idx: Long, request: HttpServletRequest): Response {
        try {
            val user: User = ClientUtils.getAdmin(request)
            spotService.editSpot(handleSpotDTO, idx, ClientUtils.getIp(request), user)
            return Response("수정 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    @DeleteMapping("/deleteSpot/{idx}")
    @ApiOperation(value = "스팟 삭제 (관리자)", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = Response::class),
        ApiResponse(code = 404, message = "해당 스팟 또는 코스 없음.", response = Response::class)
    ])
    fun deleteSpot(@PathVariable idx: Long, request: HttpServletRequest): Response {
        try {
            val user: User = ClientUtils.getAdmin(request)
            spotService.deleteSpot(idx, ClientUtils.getIp(request), user)
            return Response("삭제 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    
    @GetMapping("/getSpots")
    @ApiOperation(value = "스팟 목록 조회", authorizations = [Authorization("Bearer Token")])
    fun getSpots(@RequestParam(required = true) @Min(1) page: Int,
                 @RequestParam(required = true) @Min(1) size: Int,
                 @RequestParam(required = true) courseIdx: Long,
                 request: HttpServletRequest): GetSpotsRO {
        try {
            val user: User = ClientUtils.getUser(request)
            return spotService.getSpots(page, size, courseIdx, user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    @GetMapping("/getSpot/{idx}")
    @ApiOperation(value = "스팟 조회", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = GetSpotRO::class),
        ApiResponse(code = 404, message = "해당 스팟 없음.", response = Response::class)
    ])
    fun getSpot(@PathVariable idx: Long, request: HttpServletRequest): GetSpotRO {
        try {
            val user: User = ClientUtils.getUser(request)
            return spotService.getSpot(idx, user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    @GetMapping("/getQuizByNfc/{idx}")
    @ApiOperation(value = "NFC 코드로 퀴즈 조회 (NFC 찍기)", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = GetQuizRO::class),
        ApiResponse(code = 404, message = "일치하지 않는 스팟", response = Response::class),
        ApiResponse(code = 409, message = "이미 완료한 스팟", response = Response::class),
    ])
    fun getQuizByNfc(@PathVariable idx: Long, @RequestParam(required = true) nfcCode: String,
                     request: HttpServletRequest): GetQuizRO {
        try {
            val user: User = ClientUtils.getUser(request)
            return spotService.getQuizByNfc(idx, nfcCode, user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    @PostMapping("/completeQuiz/{idx}")
    @ApiOperation(value = "NFC 퀴즈 풀기", authorizations = [Authorization("Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = CompleteQuizRO::class),
        ApiResponse(code = 404, message = "일치하지 않는 스팟", response = Response::class),
        ApiResponse(code = 409, message = "이미 완료한 스팟", response = Response::class),
    ])
    fun completeQuiz(@PathVariable idx: Long, @RequestBody completeQuizDTO: CompleteQuizDTO,
                     request: HttpServletRequest): CompleteQuizRO {
        try {
            val user: User = ClientUtils.getUser(request)
            return spotService.completeQuiz(idx, completeQuizDTO.nfcCode, completeQuizDTO.answer, user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
}