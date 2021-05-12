package com.flash21.giftrip.lib

import com.flash21.giftrip.domain.entity.User
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest

class ClientUtils {
    
    companion object {
        
        fun getUser(request: HttpServletRequest): User {
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
        
        fun getIp(request: HttpServletRequest): String {
            var ip = request.getHeader("X-Forwarded-For")
            
            if (ip == null) {
                ip = request.getHeader("Proxy-Client-IP")
            }
            if (ip == null) {
                ip = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ip == null) {
                ip = request.getHeader("HTTP_CLIENT_IP")
            }
            if (ip == null) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR")
            }
            if (ip == null) {
                ip = request.remoteAddr
            }
            
            return ip
        }
    }
    
}