package com.flash21.giftrip.domain.dto.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class RegisterDTO {

    @NotBlank
    @Size(min = 1, max = 20)
    val phoneNumber: String = ""

    @NotBlank
    @Size(min = 1, max = 128)
    val pw: String = ""

    @NotBlank
    @Size(min = 1, max = 45)
    val name: String = ""

    @NotBlank
    val code: String = ""

}