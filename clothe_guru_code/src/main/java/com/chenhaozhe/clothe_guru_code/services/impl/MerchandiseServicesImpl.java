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
import com.chenhaozhe.clothe_guru_code.services.MerchandiseServices;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import com.chenhaozhe.clothe_guru_code.util.FTPUtil;
import com.chenhaozhe.clothe_guru_code.util.GenerateFormattedImagePath;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class MerchandiseServicesImpl implements MerchandiseServices {
    @Resource
    private MerchandiseMapper merchandiseMapper;
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
        MerchandiseWrapper merchandiseWrapper = MerchandiseWrapper.builder()
                .keyWord(merchandiseGetterDTO.getKeyWord() != null ? merchandiseGetterDTO.getKeyWord() : null)
                .keyWordLabel(merchandiseGetterDTO.getKeyWordLabel() != null ? MerchandiseKeyWordLabelEnum.values()[merchandiseGetterDTO.getKeyWordLabel()].getLabel() : null)
                .orderLabel(Objects.equals(merchandiseGetterDTO.getOrderLabel(), null) ? null : MerchandiseOrderLabelEnum.values()[merchandiseGetterDTO.getOrderLabel()].getLabel())
                .orderRule(Objects.equals(merchandiseGetterDTO.getOrderRule(), true) ? "ASC" : "DESC")
                .highPrice(Objects.equals(merchandiseGetterDTO.getHighPrice(), null) ? null : merchandiseGetterDTO.getHighPrice())
                .lowPrice(Objects.equals(merchandiseGetterDTO.getLowPrice(), null) ? null : merchandiseGetterDTO.getLowPrice())
                .pageSize(defaultPageSize)
                .offset(offset)
                .build();
        List<MerchandiseVo> list = merchandiseMapper.getMerchandise(merchandiseWrapper).stream().map(item
                -> MerchandiseConverter.merchandiseEntityToVo(item)).toList();
        Integer count = merchandiseMapper.getMerchandiseCount(merchandiseWrapper);

        return MerchandiseAndCountVo.builder().merchandiseVoList(list).count(count).build();


    }

    @Override
    public void getMerchandiseById(Integer merchandiseId) {
        ViewMerchandiseEntity merchandiseById = merchandiseMapper.getMerchandiseById(merchandiseId);
        //转化成vo
    }

    @Override
    public Integer deleteMerchandise(Integer merchandiseId) {
        return merchandiseMapper.deleteMerchandise(merchandiseId);
    }

    @Override
    @Transactional
    public void insertNewMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseInsertDTO merchandiseInsertDTO) {
        // 对图片进行处理。并且在上传失败的时候能够自动回滚事务
        String mainImgPath = "merchandise/" + GenerateFormattedImagePath.generateNewPath(mainImg.getOriginalFilename());
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
                    String itemPath = "merchandise/" + GenerateFormattedImagePath.generateNewPath(item.getOriginalFilename());
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
                .lowPrice(merchandiseInsertDTO.getLowPrice())
                .highPrice(merchandiseInsertDTO.getHighPrice())
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
        List<String> updatedSubImgList = List.of(merchandiseUploadDTO.getSubImg());
        if (!Objects.equals(mainImg, null)) {
            //当需传输的主图非空的时候，就代表需要上传新的图片
            updatedMainImgPath = GenerateFormattedImagePath.generateNewPath(mainImg.getOriginalFilename());
            try {
                checkFileNotUploadException(FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword,
                        imgPath + "merchandise/" + updatedMainImgPath, mainImg.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (subImg.length > 0) {
            //当需传输的副图的数组非空的时候，就代表需要上传新的图片，上传图片之后的图片地址添加到修改后的副图数组之后
            Arrays.stream(subImg).forEach(item -> {
                String updateSubItemPath = GenerateFormattedImagePath.generateNewPath(item.getOriginalFilename());
                try {
                    checkFileNotUploadException(FTPUtil.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword,
                            imgPath + updateSubItemPath, item.getInputStream()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                updatedSubImgList.add(updateSubItemPath);
            });

            //构建实体类
            MerchandiseEntity uploadMerchandise = MerchandiseEntity.builder()
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

    }

    @Override
    public List<CategoryEntity> getCategory(Integer page) {
        Integer offset = page * defaultPageSize;
        return merchandiseMapper.queryAllCategory(defaultPageSize, offset);
    }

    @Override
    public List<MaterialEntity> getMaterial(Integer page) {
        return merchandiseMapper.queryAllMaterial();
    }

    @Override
    public Integer deleteCategoryById(Integer categoryId) {
        return merchandiseMapper.deleteCategoryById(categoryId);
    }

    @Override
    public Integer deleteMaterialById(Integer materialId) {
        return merchandiseMapper.deleteMaterialById(materialId);
    }

    @Override
    public Integer addCategory(String categoryName, String alias) {
        if (merchandiseMapper.insertCategory(categoryName, alias) > 0) {
            Integer selectedId = merchandiseMapper.queryCategoryByName(categoryName);
            return selectedId;
        } else {
            throw new DatabaseNotChangeException("数据插入失败");
        }
    }

    @Override
    public Integer addMaterial(String materialName, String materialDescription, String reconstructionCoefficient, String alias) {
        if(merchandiseMapper.insertMaterial(materialName, materialDescription, reconstructionCoefficient, alias) > 0){
            return merchandiseMapper.queryMaterialByMaterialName(materialName);
        }else{
            throw new DatabaseNotChangeException("数据插入失败");
        }
    }

    @Override
    public Integer updateCategory(Integer categoryId, String categoryName, String alias) {
        return merchandiseMapper.updateCategoryById(categoryId, alias, categoryName);
    }

    @Override
    public Integer updateMaterial(Integer materialId, String materialName, String materialDescription,
                                  String reconstructionCoefficient, String alias) {
        return merchandiseMapper.updateMaterialById(materialId, materialName, materialDescription, reconstructionCoefficient,alias);
    }

    private void checkFileNotUploadException(Boolean success) {
        if (!success) {
            throw new DatabaseNotChangeException("文件上传失败，请重试或者尝试联系管理员！！！");
        }
    }
}


