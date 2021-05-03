package com.flash21.giftrip.domain.dto.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class LoginDTO {

    @NotBlank
    @Size(min = 1, max = 20)
    var phoneNumber: String = ""

    @NotBlank
    @Size(min = 1, max = 128)
    var pw: String = ""

}