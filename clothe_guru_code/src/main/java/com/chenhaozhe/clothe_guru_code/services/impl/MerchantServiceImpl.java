package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.MerchantMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.MerchantConverter;
import com.chenhaozhe.clothe_guru_code.model.dto.ImageDataDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchantUpdateDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantKeyWordEnum;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchantOrderValueEnum;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import com.chenhaozhe.clothe_guru_code.services.MerchantServices;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.analysis.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantServices {
    @Resource
    private MerchantMapper merchantMapper;

    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;

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
    public List<MerchantVo> getMerchant(Integer page, String keyWord, Short keyState, Short orderValue, Boolean orderRule) {
        Integer offset = defaultPageSize * page;
        String stateKey = !Objects.equals(keyState, null) ? MerchantKeyWordEnum.values()[keyState].getLabel() : null;
        String orderKey = !Objects.equals(orderValue, null) ? MerchantOrderValueEnum.values()[orderValue].getLabel() : null;
        List<MerchantEntity> merchantEntities = merchantMapper.selectAllMerchant(defaultPageSize, offset, keyWord, stateKey, orderKey, orderRule);
        return merchantEntities.stream().map(item -> MerchantConverter.EntityToVo(item)).toList();
    }

    @Override
    public void deleteMerchantByMerchantId(Long merchantId) {
        Integer backState = merchantMapper.deleteMerchantByMerchantId(merchantId);
        if (backState < 1) {
            throw new DatabaseNotChangeException("当前删除不成功，请联系管理员！！！");
        }
    }

    @Override
    public MerchantVo updateMerchant(String metaData, List<MultipartFile> images) {
        MerchantUpdateDTO merchantUpdateDTO = JackonUtil.JsonToObject(metaData, MerchantUpdateDTO.class);
        AtomicReference<Boolean> success = new AtomicReference<>(false);
        List<String> fileNameList = new ArrayList<>();
        images.forEach(item -> {
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
            merchantUpdateDTO.getCertification().add(ImageDataDTO.builder().key(merchantUpdateDTO.getCertificationText().get(i)).value(fileNameList.get(i)).build());
        }
        MerchantEntity merchantEntity = MerchantConverter.merchantDTOTOEntity(merchantUpdateDTO);
        try {
            Map<String, String> propertyValueMapListSnackCase = ClassPropertyValueMap.getPropertyValueMapListSnackCase(merchantEntity);
            log.info("转化后的数据:{}",propertyValueMapListSnackCase);
            Integer res = merchantMapper.updateMerchant(propertyValueMapListSnackCase);
            if(res > 0){
                // 重新获取对应id的数据项并返回
                MerchantVo merchantVo = updateMerchantByMerchantId(merchantUpdateDTO.getMerchantId());
                return merchantVo;
            }else{
                throw new DatabaseNotChangeException("数据没有正确修改，请联系管理员！！！");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void updateMerchant(ModifyMerchantDTO modifyMerchantDTO) {
//        try {
//            Map<String, String> propertyValueMapListSnackCase = ClassPropertyValueMap.getPropertyValueMapListSnackCase(modifyMerchantDTO);
//            Integer backState = merchantMapper.insertMerchant(propertyValueMapListSnackCase);
//            if (backState < 1){
//                throw new DatabaseNotChangeException("删除失败，请告知管理员！！！");
//            }
//            // 更新cache
//            updateMerchantByMerchantId(modifyMerchantDTO.getMerchantId());
//        } catch (IllegalAccessException e) {
//            throw new CustomInputMismatchException("您的部分输入为空，请检查您的输入！！！");
//        }
//    }

    @Cacheable(key = "#merchantId", value = "merchantCache")
    @Override
    public MerchantVo getMerchantByMerchantId(Long merchantId) {
        // 商家第一次访问时，添加vo到缓存中，接下来的每次访问都会从缓存中获取，并刷新过期时间
        return MerchantConverter.EntityToVo(merchantMapper.selectMerchantByMerchantId(merchantId));
    }

    @CachePut(key = "#merchantId", value = "merchantCache")
    public MerchantVo updateMerchantByMerchantId(Long merchantId) {
        // 商家修改信息时，会刷新缓存中的信息与过期时间。
        return MerchantConverter.EntityToVo(merchantMapper.selectMerchantByMerchantId(merchantId));
    }

    @CacheEvict(key = "#merchantId",value = "merchantCache")
    // 商家退出登录清除缓存(实际上是商家对应用户退出登录，此处可能会增加耦合度)
    public void merchantQuit(Long merchantId) {
    }
}
