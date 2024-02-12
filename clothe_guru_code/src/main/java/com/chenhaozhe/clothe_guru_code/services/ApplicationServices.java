package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationReplyDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationRequestDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.ApplicationAndCountVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationServices {
    void addNewApplication(ApplicationRequestDTO registerMesg, List<MultipartFile> files);
    ApplicationAndCountVo userQueryApplications(Long userId, Integer page);
    ApplicationAndCountVo adminQueryAllApplications(Integer page);
    ApplicationAndCountVo adminQueryAllApplications(String keyWord,Integer page,String keyState);
    void updateApplication(ApplicationReplyDTO applicationReplyDTO);



}
