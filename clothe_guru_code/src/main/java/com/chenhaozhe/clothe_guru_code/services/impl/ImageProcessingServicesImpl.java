package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.services.ImageProcessingServices;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class ImageProcessingServicesImpl implements ImageProcessingServices {
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
    final String pathPrefix = "pre-prodessingImage/";

    @Override
    public Boolean changeColor(String type, String imageName, String imageBase64, String color) {
        byte[] imageBytes = Base64.getDecoder().decode(imageBase64.substring(imageBase64.indexOf(",") + 1));
        try (ByteArrayInputStream abis = new ByteArrayInputStream(imageBytes)) {
            return FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword, imgPath + pathPrefix + imageName, abis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ExecuteRepair(String type, String imageName, String imageBase64, String maskName, String maskBase64) {

    }

    @Override
    public void ExecuteTagTranslation(String type, String imageName, String imageBase64, String source, String target) {

    }
}
