package com.flash21.giftrip.service.spot

import com.flash21.giftrip.domain.dto.spot.HandleSpotDTO
import com.flash21.giftrip.domain.entity.Spot
import com.flash21.giftrip.domain.entity.User
import com.flash21.giftrip.domain.repository.CourseRepo
import com.flash21.giftrip.domain.repository.SpotRepo
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class SpotServiceImpl : SpotService {
    
    @Autowired
    private lateinit var spotRepo: SpotRepo
    
    @Autowired
    private lateinit var courseRepo: CourseRepo
    
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
    
}