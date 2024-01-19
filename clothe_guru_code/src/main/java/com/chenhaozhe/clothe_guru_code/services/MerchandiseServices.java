package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseUploadDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface MerchandiseServices {
    void getMerchandise(MerchandiseGetterDTO merchandiseGetterDTO);

    void getMerchandiseById(Integer merchandiseId);

    Integer deleteMerchandise(Integer merchandiseId);

    void insertNewMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseInsertDTO merchandiseInsertDTO);

    void updateMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseUploadDTO merchandiseUploadDTO);

    List<CategoryEntity> getCategory(Integer page);

    List<MaterialEntity> getMaterial(Integer page);

    Integer deleteCategoryById(Integer categoryId);

    Integer deleteMaterialById(Integer materialId);

    Integer addCategory(String categoryName);

    Integer addMaterial(String materialName, String materialDescription, BigDecimal reconstructionCoefficient);

    Integer UpdateMaterial(Integer materialId, String materialName, String materialDescription, BigDecimal reconstructionCoefficient);
}
