package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.ModifyMerchantDTO;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MerchantServices {
    List<MerchantVo> getMerchant(Integer page, String keyWord, Short keyState, Short orderValue, Boolean orderRule);
    MerchantVo getMerchantByMerchantId(Long merchantId);
    void deleteMerchantByMerchantId(Long merchantId);
    MerchantVo updateMerchant(String metaData, List<MultipartFile> images);
}
