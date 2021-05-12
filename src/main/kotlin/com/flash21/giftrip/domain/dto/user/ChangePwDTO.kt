package com.flash21.giftrip.domain.dto.user

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class ChangePwDTO {
    
    @NotBlank
    @Size(min = 1, max = 128)
    val pw: String = ""
    
}