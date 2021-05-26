package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.dto.user.ChangePwDTO
import com.flash21.giftrip.domain.dto.user.EditMyInfoDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.domain.ro.user.GetMyInfoRO
import com.flash21.giftrip.lib.ClientUtils
import com.flash21.giftrip.service.user.UserServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
@Validated
class UserController {
    
    @Autowired
    private lateinit var userService: UserServiceImpl
    
    @GetMapping("/getMyInfo")
    @ApiOperation(value = "내 정보 조회 By Token", authorizations = [Authorization("Bearer Token")])
    fun getMyInfo(request: HttpServletRequest): GetMyInfoRO {
        val user: User = ClientUtils.getUser(request)
        return userService.getMyInfo(user)
    }
    
    @PatchMapping("/editMyInfo")
    @ApiOperation(value = "내 정보 수정 By Token", authorizations = [Authorization("Bearer Token")])
    fun editMyInfo(@RequestBody editMyInfoDTO: EditMyInfoDTO, request: HttpServletRequest): Response {
        val user: User = ClientUtils.getUser(request)
        userService.editMyInfo(user, editMyInfoDTO)
        return Response("수정 성공.")
    }
    
    @PatchMapping("/changePw")
    @ApiOperation(value = "비밀번호 변경 By Token", authorizations = [Authorization(value = "Bearer Token")])
    fun changePw(@RequestBody changePwDTO: ChangePwDTO, request: HttpServletRequest): Response {
        val user: User = request.getAttribute("user") as User
        userService.changePw(changePwDTO, user)
        return Response("변경 성공.")
    }
    
}