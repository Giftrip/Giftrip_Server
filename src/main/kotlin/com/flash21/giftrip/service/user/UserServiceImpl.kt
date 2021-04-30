package com.flash21.giftrip.service.user

import com.flash21.giftrip.domain.dto.auth.ChangePwDTO
import com.flash21.giftrip.domain.dto.user.EditMyInfoDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.UserRepo
import com.flash21.giftrip.domain.ro.user.GetMyInfoRO
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepo: UserRepo

    override fun getMyInfo(user: User): GetMyInfoRO {
        return ModelMapper().map(user, GetMyInfoRO::class.java)
    }

    override fun editMyInfo(user: User, editMyInfoDTO: EditMyInfoDTO) {
        user.name = editMyInfoDTO.name
        userRepo.save(user)
    }

    override fun changePw(changePwDTO: ChangePwDTO, user: User) {
        user.pw = changePwDTO.pw
        userRepo.save(user)
    }

}