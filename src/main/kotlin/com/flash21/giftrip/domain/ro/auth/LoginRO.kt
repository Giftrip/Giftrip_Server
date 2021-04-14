package com.flash21.giftrip.domain.ro.auth

import com.flash21.giftrip.domain.ro.user.GetMyInfoRO

class LoginRO(val info: GetMyInfoRO, val token: TokenRO) {
}