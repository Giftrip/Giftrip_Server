package com.flash21.giftrip.domain.entity

import java.util.*
import javax.persistence.*

@Entity
class Notice {

    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    // 제목
    @Column(length = 50, nullable = false)
    var title: String = ""

    // 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String = ""

    // 썸네일 리스트
    @ElementCollection
    var thumbnails: MutableList<String> = mutableListOf()

    // 생성 날짜
    @Column(nullable = false)
    val createdAt: Date = Date()

}