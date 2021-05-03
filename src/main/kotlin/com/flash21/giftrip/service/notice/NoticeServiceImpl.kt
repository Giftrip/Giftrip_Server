package com.flash21.giftrip.service.notice

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.Notice
import com.flash21.giftrip.domain.entity.NoticeView
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.NoticeRepo
import com.flash21.giftrip.domain.repository.NoticeViewRepo
import com.flash21.giftrip.domain.ro.notice.GetNoticeRO
import com.flash21.giftrip.domain.ro.notice.GetNoticesRO
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class NoticeServiceImpl: NoticeService {

    @Autowired
    private lateinit var noticeRepo: NoticeRepo

    @Autowired
    private lateinit var noticeViewRepo: NoticeViewRepo

    override fun createNotice(handleNoticeDTO: HandleNoticeDTO, ip: String, user: User) {
        val notice: Notice = Notice()
        notice.title = handleNoticeDTO.title
        notice.content = handleNoticeDTO.content
        notice.thumbnail = handleNoticeDTO.thumbnail
        notice.ip = ip
        noticeRepo.save(notice)
    }

    override fun editNotice(handleNoticeDTO: HandleNoticeDTO, idx: Long) {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
        notice.title = handleNoticeDTO.title
        notice.content = handleNoticeDTO.content
        notice.thumbnail = handleNoticeDTO.thumbnail
        noticeRepo.save(notice)
    }

    override fun deleteNotice(idx: Long) {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
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