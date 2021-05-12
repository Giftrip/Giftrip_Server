package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Spot
import com.flash21.giftrip.domain.entity.SpotLog
import com.flash21.giftrip.domain.entity.User
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface SpotLogRepo : JpaRepository<SpotLog, Long> {
    fun findBySpotAndUser(spot: Spot, user: User): SpotLog?
    fun findAllByUser(user: User, sort: Sort): List<SpotLog>
    fun countByCourseIdxAndUserIdx(courseIdx: Long, userIdx: Long): Long
}