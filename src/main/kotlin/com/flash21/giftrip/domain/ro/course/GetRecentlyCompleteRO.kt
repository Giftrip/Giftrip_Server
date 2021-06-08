package com.flash21.giftrip.domain.ro.course

import com.flash21.giftrip.domain.entity.GiftLog
import com.flash21.giftrip.lib.ClientUtils

class GetRecentlyCompleteRO(giftLog: GiftLog) {
    
    val idx = giftLog.user?.idx
    val name = giftLog.user?.name
    val profileImage = if (giftLog.user?.profileImage == null) null else ClientUtils.getImage(giftLog.user?.profileImage!!)
    val completedAt = giftLog.endAt
    
}