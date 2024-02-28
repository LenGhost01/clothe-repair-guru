package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.ApplicationsMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.ApplicationConverter;
import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationReplyDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationRequestDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.ApplicationAndCountVo;
import com.chenhaozhe.clothe_guru_code.services.ApplicationServices;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import com.chenhaozhe.clothe_guru_code.util.SnowFlake;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.runtime.DotClass;
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
@Slf4j
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
    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;

    @Override
    @Transactional
    public void addNewApplication(ApplicationRequestDTO registerMesg, List<MultipartFile> files) {
        // 解析注册信息和传输文件,并且在文件传输异常时及时回滚事务，避免错误插入。
        AtomicReference<Boolean> success = new AtomicReference<>(false);
        Map<String, String> fileMap = new ConcurrentHashMap<String, String>();
        List<String> fileNameList = new ArrayList<>();
        files.forEach(item -> {
            String fileName = "certification/" + item.getOriginalFilename();
            try (InputStream inputStream = item.getInputStream()) {
                success.set(FTPUtil.uploadFile(ftpHost, Integer.parseInt(ftpPort), user, password,
                        // 设置传输的文件编码为UTF-8格式
                        imgPath + fileName, inputStream));
                if (!success.get()) {
                    throw new DatabaseNotChangeException("文件上传失败,请联系管理员");
                }
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
            fileNameList.add(fileName);
        });
        for (int i = 0; i < fileNameList.size(); i++) {
            fileMap.put(registerMesg.getCertificationText().get(i), fileNameList.get(i));
        }
        String applicationMesg = JackonUtil.MapToJson(fileMap);
        ApplicationsEntity applicationEntity = ApplicationsEntity.builder()
                .userId(Long.valueOf(registerMesg.getUserId()))
                .address(registerMesg.getAddress())
                .email(registerMesg.getEmail())
                .phone(registerMesg.getPhone())
                .certification(applicationMesg)
                .introduce(registerMesg.getIntroduce())
                .merchantName(registerMesg.getMerchantName())
                .build();
        try {
            Map<String, String> applicationsMap = ClassPropertyValueMap.getPropertyValueMapListSnackCase(applicationEntity);
            Integer res = applicationsMapper.InsertApplicationByUserId(applicationsMap);
            if (res < 1) {
                throw new DatabaseNotChangeException("文件上传失败,请联系管理员");
            }
        } catch (IllegalAccessException e) {
            throw new DatabaseNotChangeException("文件上传失败,请联系管理员");
        }
    }

    @Override
    public ApplicationAndCountVo userQueryApplications(Long userId, Integer page) {
        Integer offset = page * defaultPageSize;
        List<ApplicationsEntity> applicationsEntities = applicationsMapper.selectApplicationsByUserId(userId, defaultPageSize, offset);
        Integer count = applicationsMapper.selectUsersApplicationsCount(userId);
        return ApplicationAndCountVo.builder()
                .applicationsEntityList(applicationsEntities.stream().map(item-> ApplicationConverter.ApplicationEntityToVo(item)).toList())
                .count(count)
                .build();
    }

    @Override
    public ApplicationAndCountVo adminQueryAllApplications(Integer page) {
        Integer offset = page * defaultPageSize;
        List<ApplicationsEntity> applicationsEntities = applicationsMapper.selectApplications(defaultPageSize, offset);
        Integer count = applicationsMapper.selectApplicationsCount();
        return ApplicationAndCountVo.builder()
                .applicationsEntityList(applicationsEntities.stream().map(item-> ApplicationConverter.ApplicationEntityToVo(item)).toList())
                .count(count)
                .build();
    }

    @Override
    public ApplicationAndCountVo adminQueryAllApplications(String keyWord, Integer page, String keyState) {
        Integer offset = page * defaultPageSize;
        List<ApplicationsEntity> applicationsEntities = applicationsMapper.selectApplicationsLikeKeyWord(defaultPageSize, offset, keyState, "%" + keyWord + "%");
        Integer count = applicationsMapper.selectApplicationsCount();
        return ApplicationAndCountVo.builder()
                .applicationsEntityList(applicationsEntities.stream().map(item-> ApplicationConverter.ApplicationEntityToVo(item)).toList())
                .count(count)
                .build();
    }

    @Override
    @Transactional
    public void updateApplication(ApplicationReplyDTO applicationReplyDTO) {
        log.info("接收到的邮箱是{}",applicationReplyDTO.getEmail());
        Integer res = applicationsMapper.updateApplicationState(applicationReplyDTO.getApplicationId(), applicationReplyDTO.getUserId()
                , new SnowFlake(1, 1).nextId(), applicationReplyDTO.getMerchantName(), applicationReplyDTO.getPhone(),
                applicationReplyDTO.getEmail(), applicationReplyDTO.getAddress(), applicationReplyDTO.getIntroduce(),
                applicationReplyDTO.getAuditState(), applicationReplyDTO.getCertification());

        if(res == 0){
            throw new DatabaseNotChangeException("数据库无变化");
        }

    }

}
