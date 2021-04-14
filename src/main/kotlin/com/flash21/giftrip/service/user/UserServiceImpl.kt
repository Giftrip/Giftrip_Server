package com.flash21.giftrip.service.user

import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.user.GetMyInfoRO
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    override fun getMyInfo(user: User): GetMyInfoRO {
        return ModelMapper().map(user, GetMyInfoRO::class.java)
    }
}