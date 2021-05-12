package com.flash21.giftrip.domain.ro.spot

import com.flash21.giftrip.domain.entity.Spot

class GetQuizRO(spot: Spot) {
    val quiz = spot.quiz
    val youtube = spot.youtube
}