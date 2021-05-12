package com.flash21.giftrip.service.file

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileServiceImpl : FileService {
    
    private var fileStorageLocation: Path = Paths.get("static/").toAbsolutePath().normalize()
    
    @Autowired
    fun uploadServiceImpl() {
        Files.createDirectories(fileStorageLocation)
    }
    
    override fun storeFile(file: MultipartFile): String {
        var fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "-" + Objects.requireNonNull(file.originalFilename))
        
        if (fileName.contains("..")) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "파일 이름 오류.")
        }
        fileName = fileName.replace("\\s".toRegex(), "")
        val targetLocation = fileStorageLocation.resolve(fileName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        
        return fileName
    }
    
    override fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 파일.")
            }
        } catch (ex: MalformedURLException) {
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 파일.")
        } catch (e: NullPointerException) {
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 파일.")
        }
    }
    
}