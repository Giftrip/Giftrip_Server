package com.flash21.giftrip.lib

import com.flash21.giftrip.domain.entity.User
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest

class GetUserByHeader {

    companion object {

        fun get(request: HttpServletRequest): User {
            val user: User? = request.getAttribute("user") as User?
            return user?: throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Jwt Filter Error")
        }

    }

}