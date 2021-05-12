package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
class GiftLog {
    
    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null
    
    // 유저 순서
    @JoinColumn(name = "user_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null
    
    // 코스 순서
    @JoinColumn(name = "course_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var course: Course? = null
    
    // 기프티콘 받은 여부
    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    var earn: Boolean = false
    
    // 기프티콘 받은 시간
    @Column(nullable = true)
    var earnedAt: Date = Date()
    
    // 기프티콘 키값
    @Column(nullable = true)
    var giftKey: String? = null
    
    // 기록 시작 시간
    @Column(nullable = false)
    var startAt: Date = Date()
    
    // 기록 중지 시간
    @Column(nullable = false)
    var endAt: Date = Date()
    
}