package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
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
    var thumbnails: List<String> = listOf()
    
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
    @Column(nullable = false, length = 255)
    var youtube: String = ""
    
    // 정답
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    var answer: Boolean = false
    
    // 풀이
    @Column(nullable = false)
    var explanation: String = ""
    
    // NFC
    @Column(columnDefinition = "TEXT", nullable = false)
    var nfcCode: String = ""
    
    // 생성 날짜
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date = Date()
    
    // 수정 날짜
    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: Date = Date()
    
}