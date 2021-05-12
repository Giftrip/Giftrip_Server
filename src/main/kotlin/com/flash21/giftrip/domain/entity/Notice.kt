package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
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
    
    // 썸네일
    @Column(nullable = true)
    var thumbnail: String? = null
    
    // 생성 날짜
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date = Date()
    
    // 수정 날짜
    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: Date = Date()
    
}
