package com.flash21.giftrip.domain.ro.spot

import com.flash21.giftrip.domain.entity.Spot

class CompleteQuizRO(spot: Spot, answer: Boolean, val courseCompleted: Boolean) {
    val explanation = spot.explanation
    val solved = answer == spot.answer
}