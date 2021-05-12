package com.flash21.giftrip.domain.dto.spot

import javax.validation.constraints.NotBlank

class CompleteQuizDTO {
    
    @NotBlank
    val nfcCode: String = ""
    
    @NotBlank
    val answer: Boolean = false
    
}