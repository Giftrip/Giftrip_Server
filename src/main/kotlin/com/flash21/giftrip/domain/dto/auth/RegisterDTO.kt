package com.flash21.giftrip.domain.dto.auth

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
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

    @JsonFormat(pattern="yyyy-MM-dd")
    val birth: Date = Date()

    @NotBlank
    val code: String = ""

}