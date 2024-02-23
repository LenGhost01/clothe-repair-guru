package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.services.ImageUploadService;
import jakarta.annotation.Resource;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imageUploader")
public class ImageUploadController {
    @Resource
    private ImageUploadService imageUploadService;
    @PostMapping("/upload")
    String upload(@RequestPart("uploadImage") MultipartFile file){
        return imageUploadService.uploadImage(file);
    }
}
