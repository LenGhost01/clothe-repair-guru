package com.chenhaozhe.clothe_guru_code.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String uploadImage(MultipartFile file);
}
