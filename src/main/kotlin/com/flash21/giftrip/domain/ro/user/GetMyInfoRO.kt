package com.flash21.giftrip.domain.ro.user

import java.util.*

class GetMyInfoRO {
    var idx: Long? = null
    var name: String = ""
    var phoneNumber: String = ""
    var profileImage: String? = null
    var birth: Date = Date()
    var admin: Boolean = false
    var createdAt: Date = Date()
}