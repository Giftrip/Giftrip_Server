package com.flash21.giftrip.domain.entity

import java.util.*
import javax.persistence.*

@Entity
class User {

    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    // 전번
    @Column(unique = true, nullable = false, length = 20)
    var phoneNumber: String? = null

    // 이름
    @Column(nullable = false, length = 45)
    var name: String? = null

    // 비번
    @Column(nullable = false, length = 128)
    var pw: String? = null

    // 관리자 여부
    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    var isAdmin: Boolean = false

    // 가입 날짜
    @Column(nullable = false)
    val createdAt: Date = Date()

}