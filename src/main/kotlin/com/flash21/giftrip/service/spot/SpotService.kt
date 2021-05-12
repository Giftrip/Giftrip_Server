package com.flash21.giftrip.service.spot

import com.flash21.giftrip.domain.dto.spot.HandleSpotDTO
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.ro.spot.CompleteQuizRO
import com.flash21.giftrip.domain.ro.spot.GetQuizRO
import com.flash21.giftrip.domain.ro.spot.GetSpotRO
import com.flash21.giftrip.domain.ro.spot.GetSpotsRO

interface SpotService {
    fun createSpot(handleSpotDTO: HandleSpotDTO, ip: String, user: User)
    fun editSpot(handleSpotDTO: HandleSpotDTO, spotIdx: Long, ip: String, user: User)
    fun deleteSpot(spotIdx: Long, ip: String, user: User)
    fun getSpots(page: Int, size: Int, idx: Long, user: User): GetSpotsRO
    fun getSpot(idx: Long, user: User): GetSpotRO
    fun getQuizByNfc(idx: Long, nfcCode: String, user: User): GetQuizRO
    fun completeQuiz(idx: Long, nfcCode: String, answer: Boolean, user: User): CompleteQuizRO
}