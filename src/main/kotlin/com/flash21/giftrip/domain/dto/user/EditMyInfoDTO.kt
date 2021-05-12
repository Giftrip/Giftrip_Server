package com.flash21.giftrip.domain.dto.user

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class EditMyInfoDTO {
    
    @NotBlank
    @Size(min = 1, max = 45)
    val name: String = ""
    
    val profileImage: String? = null
    
}