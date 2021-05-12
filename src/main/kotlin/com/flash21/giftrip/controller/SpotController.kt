package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.spot.HandleSpotDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.http.Response
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

@RestController
@RequestMapping("/spot")
class SpotController {
    
    @Autowired
    private lateinit var spotService: SpotServiceImpl
    
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    
    @PostMapping("/createSpot")
    @ApiOperation(value = "스팟 생성 (관리자)", authorizations = [Authorization(value = "Bearer Token")])
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
    fun editNotice(@RequestBody handleSpotDTO: HandleSpotDTO,
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
    fun deleteNotice(@PathVariable idx: Long, request: HttpServletRequest): Response {
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
    
}