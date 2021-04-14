package com.flash21.giftrip.domain.dto.auth

import javax.validation.constraints.NotBlank

class PhoneCheckDTO {

    @NotBlank
    val phoneNumber: String = ""

    @NotBlank
    val code: String = ""

}