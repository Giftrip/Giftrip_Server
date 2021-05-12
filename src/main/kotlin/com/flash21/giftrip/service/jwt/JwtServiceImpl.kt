package com.flash21.giftrip.service.jwt

import com.flash21.giftrip.constant.DateConstant
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.UserRepo
import com.flash21.giftrip.domain.ro.auth.AuthTokenModel
import com.flash21.giftrip.domain.ro.auth.TokenRO
import com.flash21.giftrip.enums.JwtAuth
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec


@Service
class JwtServiceImpl : JwtService {
    
    @Autowired
    private lateinit var userRepo: UserRepo
    
    @Value("\${jwt.secret.access}")
    private val secretAccessKey: String? = null
    
    @Value("\${jwt.secret.refresh}")
    private val secretRefreshKey: String? = null
    
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
    
    /**
     * 토큰 생성
     * @return Token
     */
    override fun createToken(user: User, authType: JwtAuth): AuthTokenModel {
        if (user.deleted) throw HttpClientErrorException(HttpStatus.NOT_FOUND, "탈퇴한 회원.")
        
        var expiredAt: Date = Date()
        var secretKey: String? = null
        
        secretKey = when (authType) {
            JwtAuth.ACCESS -> {
                expiredAt = Date(expiredAt.time + DateConstant().MILLISECONDS_FOR_A_HOUR * 1)
                secretAccessKey
            }
            JwtAuth.REFRESH -> {
                expiredAt = Date(expiredAt.time + DateConstant().MILLISECONDS_FOR_A_HOUR * 24 * 7)
                secretRefreshKey
            }
            else -> {
                throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
            }
        }
        
        val signInKey: Key = SecretKeySpec(secretKey!!.toByteArray(), signatureAlgorithm.jcaName)
        
        val headerMap: MutableMap<String, Any> = HashMap()
        
        headerMap["typ"] = "JWT"
        headerMap["alg"] = "HS256"
        
        val map: MutableMap<String, Any> = HashMap()
        
        map["idx"] = user.idx ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저 없음.")
        map["authType"] = authType
        
        val builder: JwtBuilder = Jwts.builder()
                .setHeaderParams(headerMap)
                .setClaims(map)
                .setExpiration(expiredAt)
                .signWith(signInKey)
        
        
        return AuthTokenModel(builder.compact(), expiredAt)
    }
    
    /**
     * 토큰 검증
     * @return User
     */
    override fun validateToken(token: String): User {
        try {
            val signingKey: Key = SecretKeySpec(secretAccessKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims: Claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .body
            
            if (claims["authType"].toString() != JwtAuth.ACCESS.toString()) {
                throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 타입이 아님.")
            }
            
            val user: User = userRepo.findById(claims["idx"].toString().toLong())
                    .orElseThrow {
                        HttpClientErrorException(HttpStatus.NOT_FOUND, "유저 없음.")
                    }
            
            if (user.deleted) throw HttpClientErrorException(HttpStatus.NOT_FOUND, "탈퇴한 회원.")
            
            return user
            
        } catch (e: ExpiredJwtException) {
            throw HttpClientErrorException(HttpStatus.GONE, "토큰 만료.")
        } catch (e: SignatureException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: RequiredTypeException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: MalformedJwtException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "토큰 없음.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    /**
     * 토큰 갱신
     * @return Token
     */
    override fun refreshToken(refreshToken: String): TokenRO {
        try {
            if (refreshToken.isEmpty() || refreshToken.trim().isEmpty()) {
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
            }
            
            val signInKey: Key = SecretKeySpec(secretRefreshKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims: Claims = Jwts.parserBuilder()
                    .setSigningKey(signInKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .body
            
            if (claims["authType"].toString() != JwtAuth.REFRESH.toString()) {
                throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 타입이 아님.")
            }
            
            val user: User = userRepo.findById(claims["idx"].toString().toLong())
                    .orElseThrow {
                        HttpClientErrorException(HttpStatus.NOT_FOUND, "유저 없음.")
                    }
            
            if (user.deleted) throw HttpClientErrorException(HttpStatus.NOT_FOUND, "탈퇴한 회원.")
            
            var newRefreshToken: AuthTokenModel? = null
            
            val exp: Date = claims.get("exp", Date::class.java)
            
            if (exp.time - Date().time <= DateConstant().MILLISECONDS_FOR_A_HOUR * 3) {
                newRefreshToken = createToken(user, JwtAuth.REFRESH)
            }
            
            return TokenRO(createToken(user, JwtAuth.ACCESS), newRefreshToken)
        } catch (e: ExpiredJwtException) {
            throw HttpClientErrorException(HttpStatus.GONE, "토큰 만료.")
        } catch (e: SignatureException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: MalformedJwtException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "토큰 없음.")
        } catch (e: RequiredTypeException) {
            e.printStackTrace()
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
}