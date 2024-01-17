package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.CustomInputMismatchException;
import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.MerchantMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.MerchantConverter;
import com.chenhaozhe.clothe_guru_code.model.dto.ModifyMerchantDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantKeyWordEnum;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantOrderValueEnum;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import com.chenhaozhe.clothe_guru_code.services.MerchantServices;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MerchantServiceImpl implements MerchantServices {
    @Resource
    private MerchantMapper merchantMapper;

    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;

    @Override
    public List<MerchantVo> getMerchant(Integer page, String keyWord, Short keyState, Short orderValue, Boolean orderRule) {
        Integer offset = defaultPageSize * page;
        String stateKey = !Objects.equals(keyState, null) ? MerchantKeyWordEnum.values()[keyState].getLabel() : null;
        String orderKey = !Objects.equals(orderValue, null) ? MerchantOrderValueEnum.values()[orderValue].getLabel() : null;
        List<MerchantEntity> merchantEntities = merchantMapper.selectAllMerchant(defaultPageSize, offset, keyWord, stateKey, orderKey, orderRule);
        return merchantEntities.stream().map(item -> MerchantConverter.EntityToVo(item)).toList();
    }

    @Override
    public MerchantVo getMerchantByUid(Long userId) {
        Long merchantId = merchantMapper.selectMerchantIdByUserId(userId);
        // 在第一次从关系型数据库中获取到信息的时候，就将数据加入到缓存中，方便后续的使用
        return getMerchantByMerchantId(merchantId);
    }

    @Override
    public void deleteMerchantByMerchantId(Long merchantId) {
        Integer backState = merchantMapper.deleteMerchantByMerchantId(merchantId);
        if(backState < 1){
            throw new DatabaseNotChangeException("当前删除不成功，请联系管理员！！！");
        }
    }

    @Override
    public void updateMerchant(ModifyMerchantDTO modifyMerchantDTO) {
        try {
            Map<String, String> propertyValueMapListSnackCase = ClassPropertyValueMap.getPropertyValueMapListSnackCase(modifyMerchantDTO);
            Integer backState = merchantMapper.insertMerchant(propertyValueMapListSnackCase);
            if (backState < 1){
                throw new DatabaseNotChangeException("删除失败，请告知管理员！！！");
            }
            // 更新cache
            updateMerchantByMerchantId(modifyMerchantDTO.getMerchantId());
        } catch (IllegalAccessException e) {
            throw new CustomInputMismatchException("您的部分输入为空，请检查您的输入！！！");
        }

    }

    @Cacheable(key = "#{merchantId}", value = "merchantCache")
    @Override
    public MerchantVo getMerchantByMerchantId(Long merchantId) {
        // 商家第一次访问时，添加vo到缓存中，接下来的每次访问都会从缓存中获取，并刷新过期时间
        return MerchantConverter.EntityToVo(merchantMapper.selectMerchantByMerchantId(merchantId));
    }

    @CachePut(key = "#{merchantId}", value = "merchantCache")
    public MerchantVo updateMerchantByMerchantId(Long merchantId) {
        // 商家修改信息时，会刷新缓存中的信息与过期时间。
        return MerchantConverter.EntityToVo(merchantMapper.selectMerchantByMerchantId(merchantId));
    }

    @CacheEvict(key = "#{merchantId}")
    // 商家退出登录清除缓存(实际上是商家对应用户退出登录，此处可能会增加耦合度)
    public void merchantQuit(Long merchantId) {
    }
}
