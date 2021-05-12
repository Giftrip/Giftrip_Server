package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Spot
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SpotRepo : JpaRepository<Spot, Long> {
    fun findAllByCourseIdx(courseIdx: Long, page: Pageable): Page<Spot>
    fun findAllByCourseIdx(courseIdx: Long): List<Spot>
    fun findByNfcCodeAndIdx(nfcCode: String, idx: Long): Optional<Spot>
}