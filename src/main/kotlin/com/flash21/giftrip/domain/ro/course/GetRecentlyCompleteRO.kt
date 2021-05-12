package com.flash21.giftrip.domain.ro.course

import com.flash21.giftrip.domain.entity.GiftLog

class GetRecentlyCompleteRO(giftLog: GiftLog) {
    
    val idx = giftLog.user?.idx
    val name = giftLog.user?.name
    val profileImage = giftLog.user?.profileImage
    val completedAt = giftLog.endAt
    
}