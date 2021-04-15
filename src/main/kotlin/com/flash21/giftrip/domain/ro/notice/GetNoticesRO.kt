package com.flash21.giftrip.domain.ro.notice

import com.flash21.giftrip.domain.entity.Notice
import org.springframework.data.domain.Page

class GetNoticesRO(page: Page<Notice>) {

    var content: List<Notice> = page.content
    var totalPage: Int = page.totalPages
    var totalElements: Long = page.totalElements

}