package com.flash21.giftrip.service.spot

import com.flash21.giftrip.domain.dto.spot.HandleSpotDTO
import com.flash21.giftrip.domain.entity.User

interface SpotService {
    fun createSpot(handleSpotDTO: HandleSpotDTO, ip: String, user: User)
    fun editSpot(handleSpotDTO: HandleSpotDTO, spotIdx: Long, ip: String, user: User)
    fun deleteSpot(spotIdx: Long, ip: String, user: User)
}