package com.flash21.giftrip.domain.dto.notice

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class HandleNoticeDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    val title: String = ""

    @NotBlank
    val content: String = ""

    @Size(min = 1, max = 255)
    val thumbnail: String = ""

}