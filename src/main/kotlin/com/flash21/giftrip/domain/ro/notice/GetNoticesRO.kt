package com.flash21.giftrip.domain.ro.notice

import com.flash21.giftrip.domain.entity.Notice
import org.springframework.data.domain.Page

class GetNoticesRO(val content: List<GetNoticeRO>, page: Page<Notice>) {

    var totalPage: Int = page.totalPages
    var totalElements: Long = page.totalElements

}