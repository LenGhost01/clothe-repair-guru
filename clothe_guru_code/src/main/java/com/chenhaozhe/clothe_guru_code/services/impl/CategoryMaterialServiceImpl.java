package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.MerchandiseMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.MerchandiseConverter;
import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.ViewMerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseVo;
import com.chenhaozhe.clothe_guru_code.services.CategoryMaterialService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryMaterialServiceImpl implements CategoryMaterialService {
    @Resource
    private MerchandiseMapper merchandiseMapper;
    @Override
    @Cacheable(key = "'category'", value = "Category")
    public List<CategoryEntity> getCategory(Integer page) {
        return merchandiseMapper.queryAllCategory();
    }

    @CachePut(key = "'category'", value = "Category")
    public List<CategoryEntity> updateMemoryCategory() {
        return merchandiseMapper.queryAllCategory();
    }

    @Override
    @Cacheable(key = "'material'", value = "Material")
    public List<MaterialEntity> getMaterial(Integer page) {
        return merchandiseMapper.queryAllMaterial();
    }

    @CachePut(key = "'material'", value = "Material")
    public List<MaterialEntity> updateMemoryMaterial() {
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
        if (merchandiseMapper.insertMaterial(materialName, materialDescription, reconstructionCoefficient, alias) > 0) {
            return merchandiseMapper.queryMaterialByMaterialName(materialName);
        } else {
            throw new DatabaseNotChangeException("数据插入失败");
        }
    }

    @Override
    public Integer updateCategory(Integer categoryId, String categoryName, String alias) {
        Integer integer = merchandiseMapper.updateCategoryById(categoryId, alias, categoryName);
        // 更新缓存
        updateMemoryCategory();
        return integer;
    }



    @Override
    public Integer updateMaterial(Integer materialId, String materialName, String materialDescription,
                                  String reconstructionCoefficient, String alias) {
        Integer integer = merchandiseMapper.updateMaterialById(materialId, materialName, materialDescription, reconstructionCoefficient, alias);
        // 更新缓存
        updateMemoryMaterial();
        return integer;
    }
}
