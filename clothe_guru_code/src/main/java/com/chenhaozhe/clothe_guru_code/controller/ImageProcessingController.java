package com.chenhaozhe.clothe_guru_code.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imageProcessing")
@Slf4j
public class ImageProcessingController {
    @PostMapping("/changeColor")
    public String ExecuteChangeColor(@RequestParam("image")MultipartFile image,@RequestParam("color")Integer[] color){
        log.info("上传的图片文件是:{},目标颜色是:{}",image.getOriginalFilename(),color);
        return null;
    }
    @PostMapping("/repair")
    public String ExecuteRepair(@RequestParam("image")MultipartFile image,@RequestParam("mask")MultipartFile mask){
        return null;
    }
    @PostMapping("/translation")
    public String ExecuteTagTranslation(@RequestParam("image")MultipartFile image,
                                        @RequestParam("source")String source,
                                        @RequestParam("target")String target){
        return null;
    }
}
