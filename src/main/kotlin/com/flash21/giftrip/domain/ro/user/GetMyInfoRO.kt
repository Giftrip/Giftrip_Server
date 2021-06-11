package com.flash21.giftrip.domain.ro.user

import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.lib.ClientUtils
import java.util.*

class GetMyInfoRO(user: User) {
    var idx: Long? = user.idx
    var name: String = user.name ?: ""
    var phoneNumber: String = user.phoneNumber ?: ""
    var profileImage: String? = if (user.profileImage == null) null else ClientUtils.getImage(user.profileImage!!)
    var birth: Date = user.birth
    var admin: Boolean = user.admin
    var createdAt: Date = user.createdAt
}