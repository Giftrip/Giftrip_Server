package com.flash21.giftrip.service.notice

import com.flash21.giftrip.domain.dto.notice.HandleNoticeDTO
import com.flash21.giftrip.domain.entity.Notice
import com.flash21.giftrip.domain.repository.NoticeRepo
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

    override fun createNotice(handleNoticeDTO: HandleNoticeDTO) {
        val notice: Notice = ModelMapper().map(handleNoticeDTO, Notice::class.java)
        noticeRepo.save(notice)
    }

    override fun editNotice(handleNoticeDTO: HandleNoticeDTO, idx: Long) {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
        notice.title = handleNoticeDTO.title
        notice.content = handleNoticeDTO.content
        notice.thumbnails = handleNoticeDTO.thumbnails
        noticeRepo.save(notice)
    }

    override fun deleteNotice(idx: Long) {
        val notice: Notice = noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
        noticeRepo.delete(notice)
    }

    override fun getNotices(page: Int, size: Int): GetNoticesRO {
        if (page < 1 || size < 1) throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
        val item = PageRequest.of(page - 1, size, Sort.by("createdAt").ascending())
        return GetNoticesRO(noticeRepo.findAll(item))
    }

    override fun getNotice(idx: Long): Notice {
        return noticeRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "글 없음.")
                }
    }

}