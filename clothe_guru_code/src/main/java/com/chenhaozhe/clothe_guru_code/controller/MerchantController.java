package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.exception.CustomInputMismatchException;
import com.chenhaozhe.clothe_guru_code.model.dto.ModifyMerchantDTO;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantKeyWordEnum;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantOrderValueEnum;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import com.chenhaozhe.clothe_guru_code.services.MerchantServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/merchant")
public class MerchantController {
    //用于对商家请求的处理
    @Resource
    private MerchantServices merchantServices;

    //管理员查询所有商家表并返回,兼具模糊查询和排序功能
    @GetMapping("/getAllMerchant")
    public List<MerchantVo> getMerchant(@RequestParam("page")Integer page,
                                        @RequestParam(required = false,value = "keyWord")String keyWord,
                                        @RequestParam(required = false,value = "keyState")Short keyState,
                                        @RequestParam(required = false,value = "orderValue")Short orderValue,
                                        @RequestParam(required = false,value = "orderRule")Boolean orderRule){


        return merchantServices.getMerchant(page,keyWord, keyState, orderValue ,orderRule);
    }
    //商家查询属于自己的商家信息并返回
    @GetMapping("/getMerchant")
    public MerchantVo getMerchantByMerchantId(@RequestParam("merchantId")Long merchantId){
        return merchantServices.getMerchantByMerchantId(merchantId);
    }
    //TODO 商家修改自己的商家信息,名称不允许改,允许修改的信息有支付方式,地址,商家描述,联系信息,证书信息
    @PostMapping("/updateMerchant")
    public MerchantVo modifyMerchant(@RequestParam("metaData") String metaData, @RequestPart("images") List<MultipartFile> images){
        return merchantServices.updateMerchant(metaData,images);
    }

    //商家注销属于自己的用户
    @GetMapping("/deleteMerchant")
    public void deleteMerchant(Long MerchantId){
        merchantServices.deleteMerchantByMerchantId(MerchantId);
    }

}
