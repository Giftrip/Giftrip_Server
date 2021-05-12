package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
class SpotLog {
    
    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null
    
    // 유저
    @JoinColumn(name = "user_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null
    
    // 스팟
    @JoinColumn(name = "spot_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var spot: Spot? = null
    
    // 코스
    @JoinColumn(name = "course_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var course: Course? = null
    
    // 생성 날짜
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date = Date()
    
}