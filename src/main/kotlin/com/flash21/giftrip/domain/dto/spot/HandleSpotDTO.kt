package com.flash21.giftrip.domain.dto.spot

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class HandleSpotDTO {
    
    @NotBlank
    @Size(min = 1, max = 55)
    val title: String = ""
    
    @NotBlank
    @Size(min = 1)
    val description: String = ""
    
    @NotBlank
    val courseIdx: Long = 0
    
    @NotBlank
    @Size(min = 1, max = 100)
    val address: String = ""
    
    @NotEmpty
    val thumbnails: List<String> = listOf()
    
    @NotBlank
    val lat: Double = 0.0
    
    @NotBlank
    val lon: Double = 0.0
    
    @NotBlank
    @Size(min = 1, max = 255)
    val quiz: String = ""
    
    @NotBlank
    @Size(min = 1, max = 255)
    val youtube: String = ""
    
    @NotBlank
    val answer: Boolean = false
    
    @NotBlank
    @Size(min = 1, max = 255)
    val explanation: String = ""
    
}