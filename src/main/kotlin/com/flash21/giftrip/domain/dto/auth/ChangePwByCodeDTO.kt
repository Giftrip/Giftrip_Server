package com.flash21.giftrip.domain.dto.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class ChangePwByCodeDTO {

    @NotBlank
    val phoneNumber: String = ""

    @NotBlank
    @Size(min = 1, max = 128)
    val pw: String = ""

    @NotBlank
    val code: String = ""

}