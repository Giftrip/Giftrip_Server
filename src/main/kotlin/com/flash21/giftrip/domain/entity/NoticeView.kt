package com.flash21.giftrip.domain.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import java.util.*
import javax.persistence.*

@Entity
class NoticeView {
    
    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null
    
    // 유저 순서
    @JoinColumn(name = "user_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null
    
    // 공지 순서
    @JoinColumn(name = "notice_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var notice: Notice? = null
    
    // 읽은 날짜
    @CreatedDate
    @Column(nullable = false)
    val createdAt: Date = Date()
    
}