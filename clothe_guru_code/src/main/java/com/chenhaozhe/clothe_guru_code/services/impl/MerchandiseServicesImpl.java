package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.MerchandiseMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.MerchandiseConverter;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseUploadDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.ViewMerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchandiseKeyWordLabelEnum;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchandiseOrderLabelEnum;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseAndCountVo;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseVo;
import com.chenhaozhe.clothe_guru_code.model.wrapper.MerchandiseWrapper;
import com.chenhaozhe.clothe_guru_code.services.CategoryMaterialService;
import com.chenhaozhe.clothe_guru_code.services.MerchandiseServices;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class MerchandiseServicesImpl implements MerchandiseServices {
    @Resource
    private MerchandiseMapper merchandiseMapper;
    @Resource
    private CategoryMaterialService categoryMaterialService;

    @Value("${store.ftp.host}")
    private String ftpHost;
    @Value("${store.ftp.port}")
    private Integer ftpPort;
    @Value("${store.ftp.user}")
    private String ftpUser;
    @Value("${store.ftp.password}")
    private String ftpPassword;
    @Value("${store.ftp.imgPath}")
    private String imgPath;
    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;

    @Override
    public MerchandiseAndCountVo getMerchandise(MerchandiseGetterDTO merchandiseGetterDTO) {
        Integer offset = merchandiseGetterDTO.getPage() * defaultPageSize;
        Map<String,String> filterParamPair = new HashMap<>();
        for(int i = 0;i<merchandiseGetterDTO.getFilterParam().length;i++){
            filterParamPair.put(merchandiseGetterDTO.getFilterParam()[i],merchandiseGetterDTO.getFilterValue()[i]);
        }
        MerchandiseWrapper merchandiseWrapper = MerchandiseWrapper.builder()
                .keyWord(merchandiseGetterDTO.getKeyWord() != "" ? merchandiseGetterDTO.getKeyWord() : null)
                .keyWordLabel(merchandiseGetterDTO.getKeyWordLabel() != null ? MerchandiseKeyWordLabelEnum.values()[merchandiseGetterDTO.getKeyWordLabel()].getLabel() : null)
                .orderLabel(Objects.equals(merchandiseGetterDTO.getOrderLabel(), "") ? null : String.valueOf(MerchandiseOrderLabelEnum.valueOf(merchandiseGetterDTO.getOrderLabel())))
                .orderRule(Objects.equals(merchandiseGetterDTO.getOrderRule(), "") ? "ASC" : "DESC")
                .highPrice(Objects.equals(merchandiseGetterDTO.getHighPrice(), "") ? null : merchandiseGetterDTO.getHighPrice())
                .lowPrice(Objects.equals(merchandiseGetterDTO.getLowPrice(), "") ? null : merchandiseGetterDTO.getLowPrice())
                .filterPair(filterParamPair)
                .pageSize(defaultPageSize)
                .offset(offset)
                .build();
        if (Objects.equals(merchandiseWrapper.getOrderLabel(),"PRICE")){
            merchandiseWrapper.setOrderLabel(Objects.equals(merchandiseWrapper.getOrderRule(),"ASC")?"LOW_PRICE":"HIGH_PRICE");
        }
        List<MerchandiseVo> list = merchandiseMapper.getMerchandise(merchandiseWrapper).stream().map(item
                -> MerchandiseConverter.merchandiseEntityToVo(item)).toList();
        Integer count = merchandiseMapper.getMerchandiseCount(merchandiseWrapper);

        return MerchandiseAndCountVo.builder().merchandiseVoList(list).count(count).build();


    }

    @Override
    public MerchandiseAndCountVo getMerchandiseById(Long merchandiseId, String keyword, Integer page) {
        Integer offset = page * defaultPageSize;
        List<ViewMerchandiseEntity> merchandiseById = merchandiseMapper.getMerchandiseById(merchandiseId, keyword, defaultPageSize, offset);
        Integer merchandiseCount = merchandiseMapper.getMerchandiseCountByKeyAndId(merchandiseId, keyword);
        List<CategoryEntity> categoryEntities = categoryMaterialService.getCategory(0);
        List<MaterialEntity> materialEntities = categoryMaterialService.getMaterial(0);
        //转化成vo
        List<MerchandiseVo> merchandiseVoList = merchandiseById.stream()
                .map(item -> {
                    MerchandiseVo merchandiseVo = MerchandiseConverter.merchandiseEntityToVo(item);
                    merchandiseVo.setCategory(JackonUtil.jsonToList(item.getCategory(), Integer.class));
                    merchandiseVo.setMaterial(JackonUtil.jsonToList(item.getMaterial(), Integer.class));
                    return merchandiseVo;
                })
                .toList();
        return MerchandiseAndCountVo.builder()
                .merchandiseVoList(merchandiseVoList)
                .count(merchandiseCount)
                .build();
    }

    @Override
    public Integer deleteMerchandise(Integer merchandiseId) {
        return merchandiseMapper.deleteMerchandise(merchandiseId);
    }

    @Override
    @Transactional
    public void insertNewMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseInsertDTO merchandiseInsertDTO) {
        // 对图片进行处理。并且在上传失败的时候能够自动回滚事务
        String mainImgPath = "merchandise/" + mainImg.getOriginalFilename();
        List<String> subImgPath = new ArrayList<String>();

        //写入图片
        try {
            InputStream inputStream = mainImg.getInputStream();
            Boolean success = FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword, imgPath + mainImgPath, inputStream);
            if (!success) {
                throw new DatabaseNotChangeException("文件上传失败，请重试或者尝试联系管理员！！！");
            }
            Arrays.stream(subImg).forEach(item -> {
                try {
                    String itemPath = "merchandise/" + item.getOriginalFilename();
                    subImgPath.add(itemPath);
                    Boolean itemSuccess = FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword, imgPath + itemPath, item.getInputStream());
                    if (!itemSuccess) {
                        throw new DatabaseNotChangeException("文件上传失败，请重试或者尝试联系管理员！！！");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 写入过程没有错误
        MerchandiseEntity merchandise = MerchandiseEntity.builder()
                .mainImg(mainImgPath)
                .subImg(JackonUtil.ListToJson(subImgPath))
                .merchandiseName(merchandiseInsertDTO.getMerchandiseName())
                .merchandiseDescription(merchandiseInsertDTO.getMerchandiseDescription())
                .belongs(merchandiseInsertDTO.getMerchantId())
                .lowPrice(new BigDecimal(merchandiseInsertDTO.getLowPrice()))
                .highPrice(new BigDecimal(merchandiseInsertDTO.getHighPrice()))
                .category(JackonUtil.ListToJson(List.of(merchandiseInsertDTO.getCategory())))
                .material(JackonUtil.ListToJson(List.of(merchandiseInsertDTO.getMaterial())))
                .build();

        try {
            Map<String, String> propertyValueMapListSnackCase = ClassPropertyValueMap.getPropertyValueMapListSnackCase(merchandise);
            Integer integer = merchandiseMapper.insertMerchandise(propertyValueMapListSnackCase);
            if (integer < 1) {
                throw new DatabaseNotChangeException("插入新数据失败，请重试或者尝试联系管理员！！！");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 所有输入都没有问题。返回状态码200
    }

    @Override
    public void updateMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseUploadDTO merchandiseUploadDTO) {
        String updatedMainImgPath = null;
        List<String> updatedSubImgList = new ArrayList<>(List.of(merchandiseUploadDTO.getOldSubImg()));
        if (!Objects.equals(mainImg, null)) {
            //当需传输的主图非空的时候，就代表需要上传新的图片
            updatedMainImgPath = "merchandise/" + mainImg.getOriginalFilename();
            try {
                checkFileNotUploadException(FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword,
                        imgPath + updatedMainImgPath, mainImg.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!Objects.equals(subImg, null)) {
            //当需传输的副图的数组非空的时候，就代表需要上传新的图片，上传图片之后的图片地址添加到修改后的副图数组之后
            Arrays.stream(subImg).forEach(item -> {
                String updateSubItemPath = "merchandise/" + item.getOriginalFilename();
                try {
                    checkFileNotUploadException(FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword,
                            imgPath + updateSubItemPath, item.getInputStream()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                updatedSubImgList.add(updateSubItemPath);
            });
        }

        //构建实体类
        MerchandiseEntity uploadMerchandise = MerchandiseEntity
                .builder()
                .merchandiseId(merchandiseUploadDTO.getMerchandiseId())
                .merchandiseName(merchandiseUploadDTO.getMerchandiseName())
                .merchandiseDescription(merchandiseUploadDTO.getMerchandiseDescription())
                .lowPrice(merchandiseUploadDTO.getLowPrice())
                .highPrice(merchandiseUploadDTO.getHighPrice())
                .category(JackonUtil.ListToJson(Arrays.asList(merchandiseUploadDTO.getCategory())))
                .material(JackonUtil.ListToJson(Arrays.asList(merchandiseUploadDTO.getMaterial())))
                .rating(merchandiseUploadDTO.getRating())
                .mainImg(updatedMainImgPath)
                .subImg(JackonUtil.ListToJson(updatedSubImgList))
                .build();

        //格式化传输map,并调用mapper
        try {
            Integer updateCheck = merchandiseMapper.updateMerchandise(ClassPropertyValueMap.getPropertyValueMapListSnackCase(uploadMerchandise));
            if (updateCheck < 1) {
                throw new DatabaseNotChangeException("修改数据失败，请重新尝试或联系管理员！！！");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public MerchandiseVo getMerchandiseUnitById(Integer id) {
        ViewMerchandiseEntity merchandiseUnitById = merchandiseMapper.getMerchandiseUnitById(id);
        MerchandiseVo merchandiseVo = MerchandiseConverter.merchandiseEntityToVo(merchandiseUnitById);
        merchandiseVo.setCategory(JackonUtil.jsonToList(merchandiseUnitById.getCategory(), Integer.class));
        merchandiseVo.setMaterial(JackonUtil.jsonToList(merchandiseUnitById.getMaterial(), Integer.class));

        return merchandiseVo;
    }

    private void checkFileNotUploadException(Boolean success) {
        if (!success) {
            throw new DatabaseNotChangeException("文件上传失败，请重试或者尝试联系管理员！！！");
        }
    }
}


