package com.flash21.giftrip.domain.repository

import com.flash21.giftrip.domain.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepo : JpaRepository<Notice, Long>