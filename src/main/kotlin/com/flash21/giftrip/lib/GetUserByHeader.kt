package com.flash21.giftrip.lib

import com.flash21.giftrip.domain.entity.User
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest

class GetUserByHeader {

    companion object {

        fun get(request: HttpServletRequest): User {
            val user: User = request.getAttribute("user") as User?
                    ?: throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Jwt Filter Error")

            if (user.deleted) throw HttpClientErrorException(HttpStatus.NOT_FOUND, "삭제된 유저.")

            return user
        }

        fun getAdmin(request: HttpServletRequest): User {
            val user: User = request.getAttribute("user") as User?
                    ?: throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Jwt Filter Error")

            if (user.deleted) throw HttpClientErrorException(HttpStatus.NOT_FOUND, "삭제된 유저.")
            if (!user.admin) throw HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.")
            return user
        }

    }

}