package com.chenhaozhe.clothe_guru_code.services;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ImageProcessingServices {
    Boolean changeColor(String type, String imageName, String imageBase64, String color);
    void ExecuteRepair(String type,String imageName, String imageBase64, String maskName, String maskBase64);
    void ExecuteTagTranslation(String type,String imageName, String imageBase64, String source, String target);
}