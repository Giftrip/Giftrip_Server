package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Spot
import org.springframework.data.jpa.repository.JpaRepository

interface SpotRepo : JpaRepository<Spot, Long> {
    fun findAllByCourseIdx(courseIdx: Long): List<Spot>
}