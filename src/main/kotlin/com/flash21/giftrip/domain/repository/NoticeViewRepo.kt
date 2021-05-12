package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Notice
import com.flash21.giftrip.domain.entity.NoticeView
import com.flash21.giftrip.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeViewRepo : JpaRepository<NoticeView, Long> {
    fun findByUser(user: User): NoticeView?
    fun findByUserAndNotice(user: User, notice: Notice): NoticeView?
}