package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.mapper.MerchantMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.MerchantConverter;
import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantKeyWordEnum;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantOrderValueEnum;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import com.chenhaozhe.clothe_guru_code.services.MerchantServices;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MerchantServiceImpl implements MerchantServices {
    @Resource
    private MerchantMapper merchantMapper;

    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;
    @Override
    public List<MerchantVo> getMerchant(Integer page, String keyWord, Short keyState, Short orderValue, Boolean orderRule) {
        Integer offset = defaultPageSize*page;
        String stateKey = !Objects.equals(keyState,null)? MerchantKeyWordEnum.values()[keyState].getLabel():null;
        String orderKey = !Objects.equals(orderValue,null)? MerchantOrderValueEnum.values()[orderValue].getLabel():null;
        List<MerchantEntity> merchantEntities = merchantMapper.selectAllMerchant(defaultPageSize, offset, keyWord, stateKey, orderKey, orderRule);
        return merchantEntities.stream().map(item-> MerchantConverter.EntityToVo(item)).toList();
    }
}
