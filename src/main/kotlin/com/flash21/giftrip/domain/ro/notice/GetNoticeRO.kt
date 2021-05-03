package com.flash21.giftrip.domain.ro.notice

import com.flash21.giftrip.domain.entity.Notice
import java.util.*

class GetNoticeRO(item: Notice, val viewed: Boolean) {
    val idx: Long? = item.idx
    val title: String = item.title
    val content: String = item.content
    val thumbnail: String? = item.thumbnail
    val createdAt: Date = item.createdAt
}