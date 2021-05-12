package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
class Course {
    
    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null
    
    // 제목
    @Column(nullable = false, length = 55)
    var title: String = ""
    
    // 설명
    @Column(columnDefinition = "TEXT", nullable = false)
    var description: String = ""
    
    // 썸네일
    @Column(nullable = false)
    var thumbnail: String = ""
    
    // 도시 명
    @Column(nullable = false, length = 50)
    var city: String = ""
    
    // 생성 날짜
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date = Date()
    
    // 수정 날짜
    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: Date = Date()
    
}