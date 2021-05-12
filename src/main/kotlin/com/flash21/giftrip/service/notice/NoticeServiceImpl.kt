package com.flash21.giftrip.service.notice

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.Notice
import com.flash21.giftrip.domain.entity.NoticeView
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.NoticeRepo
import com.flash21.giftrip.domain.repository.NoticeViewRepo
import com.flash21.giftrip.domain.ro.notice.GetNoticeRO
import com.flash21.giftrip.domain.ro.notice.GetNoticesRO
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class NoticeServiceImpl : NoticeService {
    
    @Autowired
    private lateinit var noticeRepo: NoticeRepo
    
    @Autowired
    private lateinit var noticeViewRepo: NoticeViewRepo
    
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    
    override fun createNotice(handleNoticeDTO: HandleNoticeDTO, ip: String, user: User) {
        val notice: Notice = Notice()
        notice.title = handleNoticeDTO.title
        notice.content = handleNoticeDTO.content
        notice.thumbnail = handleNoticeDTO.thumbnail
        logger.info("$ip USER ${user.idx} - CREATE\n${Gson().toJson(notice)}")
        noticeRepo.save(notice)
    }
    
    override fun editNotice(handleNoticeDTO: HandleNoticeDTO, idx: Long, ip: String, user: User) {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
        val prevNotice: String = Gson().toJson(notice)
        notice.title = handleNoticeDTO.title
        notice.content = handleNoticeDTO.content
        notice.thumbnail = handleNoticeDTO.thumbnail
        logger.info("$ip USER ${user.idx} - PATCH\n$prevNotice\n${Gson().toJson(notice)}")
        noticeRepo.save(notice)
    }
    
    override fun deleteNotice(idx: Long, ip: String, user: User) {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
        
        logger.info("$ip USER ${user.idx} - DELETE\n${Gson().toJson(notice)}")
        noticeRepo.delete(notice)
    }
    
    override fun getNotices(page: Int, size: Int, user: User): GetNoticesRO {
        if (page < 1 || size < 1) throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
        val item = PageRequest.of(page - 1, size, Sort.by("createdAt").ascending())
        
        val notices = noticeRepo.findAll(item)
        val noticeRO: MutableList<GetNoticeRO> = mutableListOf()
        
        notices.map {
            noticeRO.add(GetNoticeRO(it, noticeViewRepo.findByUserAndNotice(user, it) != null))
        }
        
        
        return GetNoticesRO(noticeRO, notices)
    }
    
    override fun getNotice(idx: Long, user: User): GetNoticeRO {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
        var isView: Boolean = false
        
        if (noticeViewRepo.findByUser(user) == null) {
            isView = true
            val noticeView: NoticeView = NoticeView()
            noticeView.user = user
            noticeView.notice = notice
            noticeViewRepo.save(noticeView)
        }
        
        return GetNoticeRO(notice, isView)
    }
    
}