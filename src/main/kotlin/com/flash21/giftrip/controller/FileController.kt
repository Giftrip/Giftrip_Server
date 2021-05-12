package com.flash21.giftrip.controller

import com.flash21.giftrip.domain.ro.file.FileUploadRO
import com.flash21.giftrip.domain.ro.http.Response
import com.flash21.giftrip.lib.ClientUtils
import com.flash21.giftrip.service.file.FileServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.apache.commons.io.FilenameUtils
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.lang.NullPointerException
import javax.servlet.http.HttpServletRequest

/**
 * 파일 관련
 */
@RestController
@RequestMapping("/file")
class FileController {
    
    @Autowired
    private lateinit var fileService: FileServiceImpl
    
    @PostMapping("/upload")
    @ApiOperation("파일 업로드 (관리자)", authorizations = [Authorization(value = "Bearer Token")])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "성공.", response = FileUploadRO::class),
        ApiResponse(code = 400, message = "확장자 오류, 크기 오류, 검증오류", response = Response::class)
    ])
    fun uploadFile(@RequestParam(value = "file", required = true) file: MultipartFile,
                   request: HttpServletRequest): FileUploadRO {
        try {
            ClientUtils.getAdmin(request)
            if (file.isEmpty || (FilenameUtils.getExtension(file.originalFilename) != "png"
                            && FilenameUtils.getExtension(file.originalFilename) != "jpg"
                            && FilenameUtils.getExtension(file.originalFilename) != "jpeg"
                            && FilenameUtils.getExtension(file.originalFilename) != "gif"
                            && FilenameUtils.getExtension(file.originalFilename) != "svg")) {
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
            }
            return FileUploadRO(fileService.storeFile(file))
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: SizeLimitExceededException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "너무 큰 용량.")
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
    
    @GetMapping("/getImage/{fileName}")
    @ApiOperation("이미지 조회")
    @ApiResponses(value = [
        ApiResponse(code = 404, message = "해당 사진이 없음.", response = Response::class)
    ])
    fun getImage(@PathVariable(required = true) fileName: String, request: HttpServletRequest): ResponseEntity<*>? {
        if (FilenameUtils.getExtension(fileName) != "png"
                && FilenameUtils.getExtension(fileName) != "jpg"
                && FilenameUtils.getExtension(fileName) != "jpeg"
                && FilenameUtils.getExtension(fileName) != "gif"
                && FilenameUtils.getExtension(fileName) != "svg") {
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "파일 없음.")
        }
        
        val resource: Resource = fileService.loadFileAsResource(fileName)
        val contentType: String = try {
            request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (e: IOException) {
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        } catch (e: NullPointerException) {
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 파일이 이미지가 아님.")
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource)
    }
    
}