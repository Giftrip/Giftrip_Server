package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.auth.ChangePwDTO
import com.flash21.giftrip.domain.dto.user.EditMyInfoDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.user.GetMyInfoRO
import com.flash21.giftrip.lib.GetUserByHeader
import com.flash21.giftrip.service.user.UserServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @GetMapping("/getMyInfo")
    @ApiOperation(value = "내 정보 조회 By Token", authorizations = [Authorization("Bearer Token")])
    fun getMyInfo(request: HttpServletRequest): GetMyInfoRO {
        try {
            val user: User = GetUserByHeader.get(request)
            return userService.getMyInfo(user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PatchMapping("/editMyInfo")
    @ApiOperation(value = "내 정보 수정 By Token", authorizations = [Authorization("Bearer Token")])
    fun editMyInfo(@RequestBody editMyInfoDTO: EditMyInfoDTO, request: HttpServletRequest): Response {
        try {
            val user: User = GetUserByHeader.get(request)
            userService.editMyInfo(user, editMyInfoDTO)
            return Response("수정 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    @PatchMapping("/changePw")
    @ApiOperation(value = "비밀번호 변경 By Token", authorizations = [Authorization(value="Bearer Token")])
    fun changePw(@RequestBody changePwDTO: ChangePwDTO, request: HttpServletRequest): Response {
        try {
            val user: User = request.getAttribute("user") as User
            userService.changePw(changePwDTO, user)
            return Response("변경 성공.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

}