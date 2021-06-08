package com.flash21.giftrip.domain.ro.spot

import com.flash21.giftrip.domain.entity.Spot
import com.flash21.giftrip.lib.ClientUtils

class GetSpotRO(spot: Spot, val completed: Boolean) {
    val idx = spot.idx
    val courseIdx = spot.course?.idx
    val title = spot.title
    val description = spot.description
    val address = spot.address
    val thumbnails = ClientUtils.getImages(spot.thumbnails)
    val lat = spot.lat
    val lon = spot.lon
}