package com.flash21.giftrip.domain.dto.auth

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

class LoginDTO {

    @NotBlank
    @Length(min = 1, max = 20)
    val phoneNumber: String = ""

    @NotBlank
    @Length(min = 1, max = 128)
    val pw: String = ""

}