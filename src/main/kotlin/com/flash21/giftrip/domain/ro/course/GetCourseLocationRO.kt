package com.flash21.giftrip.domain.ro.course

import com.flash21.giftrip.domain.entity.Spot

class GetCourseLocationRO(spot: Spot) {
    val idx = spot.idx
    val lat = spot.lat
    val lon = spot.lon
}