package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.ApplicationsMapper;
import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationRequestDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import com.chenhaozhe.clothe_guru_code.services.ApplicationServices;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ApplicationServiceImpl implements ApplicationServices {
    @Resource
    private ApplicationsMapper applicationsMapper;

    @Value("${store.ftp.host}")
    private String ftpHost;
    @Value("${store.ftp.port}")
    private String ftpPort;
    @Value("${store.ftp.user}")
    private String user;
    @Value("${store.ftp.password}")
    private String password;
    @Value("${store.ftp.imgPath}")
    private String imgPath;

    @Override
    @Transactional
    public void addNewApplication(ApplicationRequestDTO registerMesg, List<MultipartFile> files) {
        // 解析注册信息和传输文件,并且在文件传输异常时及时回滚事务，避免错误插入。
        AtomicReference<Boolean> success = new AtomicReference<>(false);
        Map<String, String> fileMap = new ConcurrentHashMap<String, String>();
        List<String> fileNameList = new ArrayList<>();
        files.forEach(item -> {
            String fileName = item.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String transportFileName = "img_" + System.currentTimeMillis() + suffix;
            try (InputStream inputStream = item.getInputStream()) {
                success.set(FTPUtil.uploadFile(ftpHost, Integer.parseInt(ftpPort), user, password,
                        // 设置传输的文件编码为UTF-8格式
                        imgPath + transportFileName, inputStream));
                if(!success.get()){
                    throw new DatabaseNotChangeException("文件上传失败,请联系管理员");
                }
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
            fileNameList.add(transportFileName);
        });
        for (int i=0;i<fileNameList.size();i++){
            fileMap.put(registerMesg.getFileNameList().get(i), fileNameList.get(i));
        }
        String applicationMesg= JackonUtil.MapToJson(fileMap);
        applicationsMapper.InsertApplicationByUserId(registerMesg.getUserId(), registerMesg.getMerchantName(), applicationMesg);
    }

    @Override
    public List<ApplicationsEntity> userQueryApplications(Long userId) {
        return applicationsMapper.selectApplicationsByUserId(userId);
    }

    @Override
    public List<ApplicationsEntity> adminQueryAllApplications() {
        return applicationsMapper.selectApplications();
    }

    @Override
    public void updateApplication(Integer applicationId, Short auditState, String auditFeedback) {
        applicationsMapper.UpdateApplicationsById(auditState, auditFeedback, applicationId);
    }
}
