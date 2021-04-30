package com.flash21.giftrip.service.file

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun storeFile(file: MultipartFile): String
    fun loadFileAsResource(fileName: String): Resource
}