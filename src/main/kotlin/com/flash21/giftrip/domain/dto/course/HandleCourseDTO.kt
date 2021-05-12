package com.flash21.giftrip.domain.dto.course

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class HandleCourseDTO {

    @NotBlank
    @Size(min = 1, max = 55)
    val title: String = ""

    @NotBlank
    @Size(min = 1)
    val description: String = ""

    @NotBlank
    @Size(min = 1, max = 50)
    val city: String = ""

    @NotBlank
    val thumbnail: String = ""

}