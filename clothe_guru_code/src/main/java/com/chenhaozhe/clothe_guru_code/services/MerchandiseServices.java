package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseUploadDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseAndCountVo;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface MerchandiseServices {
    MerchandiseAndCountVo getMerchandise(MerchandiseGetterDTO merchandiseGetterDTO);

    void getMerchandiseById(Integer merchandiseId);

    Integer deleteMerchandise(Integer merchandiseId);

    void insertNewMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseInsertDTO merchandiseInsertDTO);

    void updateMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseUploadDTO merchandiseUploadDTO);

    List<CategoryEntity> getCategory(Integer page);

    List<MaterialEntity> getMaterial(Integer page);

    Integer deleteCategoryById(Integer categoryId);

    Integer deleteMaterialById(Integer materialId);

    Integer addCategory(String categoryName,String alias);

    Integer addMaterial(String materialName, String materialDescription, String reconstructionCoefficient,String alias);

    Integer updateMaterial(Integer materialId, String materialName, String materialDescription,
                           String reconstructionCoefficient, String alias);

    Integer updateCategory(Integer categoryId,String categoryName,String alias);
}
