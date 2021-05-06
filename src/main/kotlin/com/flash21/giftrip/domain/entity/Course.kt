package com.flash21.giftrip.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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

    // 코스 생성자
    @JoinColumn(name = "user_idx")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null

    // 생성자의 ip
    @JsonIgnore
    @Column(length = 20)
    var ip: String? = null

    // 도시 명
    @Column(nullable = false, length = 50)
    var location: String = ""

    // 생성 날짜
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date = Date()

    // 수정 날짜
    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: Date = Date()

}