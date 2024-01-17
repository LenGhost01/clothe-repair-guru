package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/merchant")
public class MerchantController {
    //用于对商家请求的处理

    //TODO 管理员查询所有商家表并返回
    @GetMapping("/getAllMerchant")
    public List<MerchantVo> getMerchant(@RequestParam("page")Integer page){

        return null;
    }
    //TODO 商家查询属于自己的商家信息并返回
    @GetMapping("/getMerchantByUid")
    public MerchantVo getMerchantByUid(@RequestParam("userId")Long userId){

        return null;
    }
    //TODO 商家修改自己的商家信息
    @PostMapping("/updateMerchant")
    public void modifyMerchant(){

    }

    //TODO 商家注销属于自己的用户
    @GetMapping("/deleteMerchant")
    public void deleteMerchant(){

    }

    //TODO 模糊查询，查询与输入字符串类似的商家
    @GetMapping("/getMerchantLikeWord")
    public List<MerchantVo> getMerchantLikeWord(@Param("keyWord")String keyWord){
        return null;
    }

}
