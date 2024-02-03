package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationReplyDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.ApplicationRequestDTO;
import com.chenhaozhe.clothe_guru_code.model.enums.ApplicationKeyWordEnum;
import com.chenhaozhe.clothe_guru_code.model.vo.ApplicationAndCountVo;
import com.chenhaozhe.clothe_guru_code.services.ApplicationServices;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/application")
public class ApplicationController {
    @Resource
    private ApplicationServices applicationServices;

    @PostMapping("/sendApplication")
    public void sendApplication(@RequestParam("regMesg") String regMesg, @RequestPart("images") List<MultipartFile> images) {
        /* TODO 实现功能：用户发送申请请求,填写的用户信息里，会包含图片文件，需要上传到nginx服务器后，将uri封装成json对象 */
        log.info("json文件:{},图片文件:{}", regMesg, images);
        ApplicationRequestDTO requestDTO = JackonUtil.JsonToObject(regMesg, ApplicationRequestDTO.class);
        applicationServices.addNewApplication(requestDTO, images);
    }

    @GetMapping("/userQueryApplication")
    public ApplicationAndCountVo userQueryApplication(@RequestParam("userId") Long userId, @RequestParam("page") Integer page) {
        // TODO 实现功能：用户查询自己的请求申请记录
        return applicationServices.userQueryApplications(userId, page);
    }

    @GetMapping("/adminQueryApplication")
    public ApplicationAndCountVo adminQueryApplication(@RequestParam("page") Integer page) {
        // TODO 实现功能：管理员查询所有的申请记录
        return applicationServices.adminQueryAllApplications(page);
    }

    @PostMapping("/adminUpdateApplication")
    public void adminUpdateApplication(@RequestBody ApplicationReplyDTO applicationReplyDTO) {
        // TODO 实现功能：管理员修改请求状态，并填写反馈，通过，则添加用户信息到商家数据库
        applicationServices.updateApplication(applicationReplyDTO.getApplicationId(),
                applicationReplyDTO.getAuditState(), applicationReplyDTO.getAuditFeedback());
    }

    @GetMapping("/getApplicationsLikeKeyWord")
    public ApplicationAndCountVo getApplicationsLikeKeyWord(@RequestParam("keyWord") String keyWord,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("keyState") Short keyState) {
        return applicationServices.adminQueryAllApplications(keyWord, page, ApplicationKeyWordEnum.values()[keyState].getLabel());

    }

}
