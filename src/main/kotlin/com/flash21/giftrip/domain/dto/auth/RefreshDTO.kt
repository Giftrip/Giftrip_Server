package com.flash21.giftrip.domain.dto.auth

import javax.validation.constraints.NotBlank

class RefreshDTO {

    @NotBlank
    val refreshToken: String = ""

}