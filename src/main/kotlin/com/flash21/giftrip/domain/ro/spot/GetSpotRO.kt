package com.flash21.giftrip.domain.ro.spot

import com.flash21.giftrip.domain.entity.Spot

class GetSpotRO(spot: Spot, val completed: Boolean) {
    val idx = spot.idx
    val courseIdx = spot.course?.idx
    val title = spot.title
    val description = spot.description
    val address = spot.address
    val lat = spot.lat
    val lon = spot.lon
}