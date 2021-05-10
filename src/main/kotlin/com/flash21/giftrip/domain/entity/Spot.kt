package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Spot {

    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    // 코스
    @JoinColumn(name = "course_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var course: Course? = null

    // 썸네일 목록
    @Column
    @ElementCollection
    var thumbnail: List<String> = listOf()

    // 제목
   @Column(nullable = false, length = 55)
   var title: String = ""

    // 설명
    @Column(columnDefinition = "TEXT", nullable = false)
    var description: String = ""

    // 주소
    @Column(nullable = false, length = 100)
    var address: String = ""

    // 위도
    @Column(nullable = false)
    var lat: Double = 0.0

    // 경도
    @Column(nullable = false)
    var lon: Double = 0.0

    // 퀴즈
    @Column(nullable = false, length = 255)
    var quiz: String = ""

    // 유튜브 링크
    @Column(nullable = false, length =  255)
    var youtube: String = ""

    // 정답
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    var answer: Boolean = false

    // 풀이
    @Column(nullable = false)
    var explain: String = ""

    // NFC
    @Column(columnDefinition = "TEXT", nullable = false)
    var nfcCode: String = ""

}