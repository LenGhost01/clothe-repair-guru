package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationRequestDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationServices {
    void addNewApplication(ApplicationRequestDTO registerMesg, List<MultipartFile> files);
    List<ApplicationsEntity> userQueryApplications(Long userId);
    List<ApplicationsEntity> adminQueryAllApplications();
    void updateApplication(Integer applicationId,Short auditState,String auditFeedback);
}
