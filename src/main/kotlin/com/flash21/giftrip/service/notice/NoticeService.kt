package com.flash21.giftrip.service.notice

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.Notice
import com.flash21.giftrip.domain.ro.notice.GetNoticesRO

interface NoticeService {
    fun createNotice(handleNoticeDTO: HandleNoticeDTO)
    fun editNotice(handleNoticeDTO: HandleNoticeDTO, idx: Long)
    fun deleteNotice(idx: Long)
    fun getNotices(pagE: Int, size: Int): GetNoticesRO
    fun getNotice(idx: Long): Notice
}