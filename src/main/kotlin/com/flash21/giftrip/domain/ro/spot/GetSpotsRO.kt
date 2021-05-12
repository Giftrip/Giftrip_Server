package com.flash21.giftrip.domain.ro.spot

import com.flash21.giftrip.domain.entity.Spot
import org.springframework.data.domain.Page

class GetSpotsRO(val content: List<GetSpotRO>, val completed: Long, page: Page<Spot>) {
    val totalPage = page.totalPages
    val totalElements = page.totalElements
}