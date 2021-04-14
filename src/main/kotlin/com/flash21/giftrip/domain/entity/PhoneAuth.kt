package com.flash21.giftrip.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.flash21.giftrip.constant.DateConstant
import java.util.*
import javax.persistence.*

@Entity
class PhoneAuth {

    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    var idx: Long? = null

    // 코드
    @Column
    var code: String? = null

    // 전화번호
    @Column(unique = true, nullable = false)
    var phoneNumber: String = ""

    // 만료시간
    @Column(nullable = false)
    var expireAt: Date = Date(Date().time + DateConstant().MILLISECONDS_FOR_A_MINUTE * 5)

    // 다시 전송 가능한 시간
    @Column(nullable = false)
    var retryAt: Date = Date()

    fun reset(code: String) {
        this.code = code
        this.retryAt = Date(Date().time + DateConstant().MILLISECONDS_FOR_A_MINUTE * 1)
        this.expireAt = Date(Date().time + DateConstant().MILLISECONDS_FOR_A_MINUTE * 5)
    }

}