package com.flash21.giftrip.service.notice

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.notice.GetNoticeRO
import com.flash21.giftrip.domain.ro.notice.GetNoticesRO

interface NoticeService {
    fun createNotice(handleNoticeDTO: HandleNoticeDTO, ip: String, user: User)
    fun editNotice(handleNoticeDTO: HandleNoticeDTO, idx: Long, ip: String, user: User)
    fun deleteNotice(idx: Long, ip: String, user: User)
    fun getNotices(page: Int, size: Int, user: User): GetNoticesRO
    fun getNotice(idx: Long, user: User): GetNoticeRO
}