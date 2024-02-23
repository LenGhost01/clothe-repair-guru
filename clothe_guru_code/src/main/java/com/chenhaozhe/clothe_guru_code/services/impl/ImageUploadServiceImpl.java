package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.services.ImageUploadService;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class ImageUploadServiceImpl implements ImageUploadService {
    @Value("${store.ftp.host}")
    private String ftpHost;
    @Value("${store.ftp.port}")
    private Integer ftpPort;
    @Value("${store.ftp.user}")
    private String ftpUser;
    @Value("${store.ftp.password}")
    private String ftpPassword;
    @Value("${store.ftp.imgPath}")
    private String imgPath;
    final String pathPrefix = "uploadedImage/";
    @Override
    public String uploadImage(MultipartFile file) {
        String pathUri = pathPrefix+file.getOriginalFilename();
        try{
            InputStream inputStream = file.getInputStream();
            Boolean res = FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword, imgPath + pathUri, inputStream);
            if(!res){
                throw new DatabaseNotChangeException("图片上传失败");
            }
            return pathUri;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
