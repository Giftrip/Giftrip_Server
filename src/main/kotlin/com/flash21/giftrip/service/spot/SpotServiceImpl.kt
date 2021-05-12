package com.flash21.giftrip.service.spot

import com.flash21.giftrip.domain.dto.spot.HandleSpotDTO
import com.flash21.giftrip.domain.entity.GiftLog
import com.flash21.giftrip.domain.entity.Spot
import com.flash21.giftrip.domain.entity.SpotLog
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.CourseRepo
import com.flash21.giftrip.domain.repository.GiftLogRepo
import com.flash21.giftrip.domain.repository.SpotLogRepo
import com.flash21.giftrip.domain.repository.SpotRepo
import com.flash21.giftrip.domain.ro.spot.CompleteQuizRO
import com.flash21.giftrip.domain.ro.spot.GetQuizRO
import com.flash21.giftrip.domain.ro.spot.GetSpotRO
import com.flash21.giftrip.domain.ro.spot.GetSpotsRO
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.util.*

@Service
class SpotServiceImpl : SpotService {
    
    @Autowired
    private lateinit var spotRepo: SpotRepo
    
    @Autowired
    private lateinit var courseRepo: CourseRepo
    
    @Autowired
    private lateinit var spotLogRepo: SpotLogRepo
    
    @Autowired
    private lateinit var giftLogRepo: GiftLogRepo
    
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    
    override fun createSpot(handleSpotDTO: HandleSpotDTO, ip: String, user: User) {
        val course = courseRepo.findById(handleSpotDTO.courseIdx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 코스 없음.")
                }
        val spot: Spot = Spot()
        spot.title = handleSpotDTO.title
        spot.description = handleSpotDTO.description
        spot.course = course
        spot.address = handleSpotDTO.address
        spot.answer = handleSpotDTO.answer
        spot.explanation = handleSpotDTO.explanation
        spot.lat = handleSpotDTO.lat
        spot.lon = handleSpotDTO.lon
        spot.quiz = handleSpotDTO.quiz
        spot.youtube = handleSpotDTO.youtube
        spot.thumbnails = handleSpotDTO.thumbnails
        spot.nfcCode = handleSpotDTO.nfcCode
        
        spotRepo.save(spot)
        logger.info("$ip USER ${user.idx} - CREATE\n${Gson().toJson(spot)}")
        
    }
    
    override fun editSpot(handleSpotDTO: HandleSpotDTO, spotIdx: Long, ip: String, user: User) {
        val spot = spotRepo.findById(spotIdx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 스팟 없음.")
                }
        val course = courseRepo.findById(handleSpotDTO.courseIdx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 코스 없음.")
                }
        val prevSpot: String = Gson().toJson(spot)
        spot.title = handleSpotDTO.title
        spot.description = handleSpotDTO.description
        spot.course = course
        spot.address = handleSpotDTO.address
        spot.answer = handleSpotDTO.answer
        spot.explanation = handleSpotDTO.explanation
        spot.lat = handleSpotDTO.lat
        spot.lon = handleSpotDTO.lon
        spot.quiz = handleSpotDTO.quiz
        spot.youtube = handleSpotDTO.youtube
        spot.thumbnails = handleSpotDTO.thumbnails
        spot.nfcCode = handleSpotDTO.nfcCode
        
        spotRepo.save(spot)
        logger.info("$ip USER ${user.idx} - PATCH\n$prevSpot\n${Gson().toJson(spot)}")
    }
    
    override fun deleteSpot(spotIdx: Long, ip: String, user: User) {
        val spot = spotRepo.findById(spotIdx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 스팟 없음.")
                }
        
        spotRepo.delete(spot)
        logger.info("$ip USER ${user.idx} - DELETE\n${Gson().toJson(spot)}")
    }
    
    override fun getSpots(page: Int, size: Int, idx: Long, user: User): GetSpotsRO {
        val course = courseRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 코스 없음.")
                }
        
        if (page < 1 || size < 1) throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
        val item = PageRequest.of(page - 1, size, Sort.by("createdAt").ascending())
        
        val spots = spotRepo.findAllByCourseIdx(idx, item)
        val spotList = mutableListOf<GetSpotRO>()
        
        spots.map {
            spotList.add(GetSpotRO(it, spotLogRepo.findBySpotAndUser(it, user) != null))
        }
        
        return GetSpotsRO(spotList, spotLogRepo.countByCourseIdxAndUserIdx(idx, user.idx!!), spots)
    }
    
    override fun getSpot(idx: Long, user: User): GetSpotRO {
        val spot = spotRepo.findById(idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 스팟 없음.")
                }
        
        return GetSpotRO(spot, spotLogRepo.findBySpotAndUser(spot, user) != null)
    }
    
    override fun getQuizByNfc(idx: Long, nfcCode: String, user: User): GetQuizRO {
        val spot = spotRepo.findByNfcCodeAndIdx(nfcCode, idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.NOT_FOUND, "일치하지 않는 NFC.")
                }
        
        if (spotLogRepo.findBySpotAndUser(spot, user) != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT, "이미 완료된 스팟.")
        }
        
        return GetQuizRO(spot)
    }
    
    override fun completeQuiz(idx: Long, nfcCode: String, answer: Boolean, user: User): CompleteQuizRO {
        val spot = spotRepo.findByNfcCodeAndIdx(nfcCode, idx)
                .orElseThrow {
                    throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "일치하지 않는 NFC.")
                }
        
        if (spotLogRepo.findBySpotAndUser(spot, user) != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT, "이미 완료된 스팟.")
        }
        
        val spotLog = SpotLog()
        spotLog.spot = spot
        spotLog.user = user
        spotLog.course = spot.course
        
        spotLogRepo.save(spotLog)
        
        val list = spotLogRepo.findAllByUser(user, Sort.by("createdAt").ascending())
        var length = 0
        var completed: Boolean = false
        
        list.map {
            if (it.spot != null && it.spot?.course == spot.course) {
                length++
            }
        }
        
        if (length != 0 && length == spotRepo.findAllByCourseIdx(spot.course!!.idx!!).size) {
            completed = true
            
            val giftLog = GiftLog()
            giftLog.course = spot.course
            giftLog.user = user
            giftLog.startAt = list[0].createdAt
            giftLog.endAt = Date()
            
            // TODO 기프티콘 키
            giftLog.giftKey = "THISISKEY"
            
            giftLogRepo.save(giftLog)
        }
        
        return CompleteQuizRO(spot, answer, completed)
    }
    
}