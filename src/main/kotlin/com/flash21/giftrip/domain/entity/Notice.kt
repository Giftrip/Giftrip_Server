package com.flash21.giftrip.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
class Notice {

    // 순서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    // 유저 순서
    @JoinColumn(name = "user_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null

    // 제목
    @Column(length = 50, nullable = false)
    var title: String = ""

    // 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String = ""

    // 썸네일
    @Column(nullable = true)
    var thumbnail: String? = null

    // 생성자의 ip
    @JsonIgnore
    @Column(length = 20)
    var ip: String? = null

    // 생성 날짜
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date = Date()

    // 수정 날짜
    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: Date = Date()

}
