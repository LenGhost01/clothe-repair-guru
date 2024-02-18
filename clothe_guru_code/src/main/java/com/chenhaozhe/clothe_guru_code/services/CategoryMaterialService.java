package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseVo;

import java.util.List;


public interface CategoryMaterialService {
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
