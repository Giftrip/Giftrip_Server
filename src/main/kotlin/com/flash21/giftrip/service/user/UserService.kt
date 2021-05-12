package com.flash21.giftrip.service.user

import com.flash21.giftrip.domain.dto.user.ChangePwDTO
import com.flash21.giftrip.domain.dto.user.EditMyInfoDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.user.GetMyInfoRO

interface UserService {
    fun getMyInfo(user: User): GetMyInfoRO
    fun editMyInfo(user: User, editMyInfoDTO: EditMyInfoDTO)
    fun changePw(changePwDTO: ChangePwDTO, user: User)
}