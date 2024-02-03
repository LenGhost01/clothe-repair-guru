package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.ViewMerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.wrapper.MerchandiseWrapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MerchandiseMapper {

    // 使用注解编写sql语句
    @Select("""
            SELECT merchandise_id, merchandise_name,merchandise_description,low_price,high_price,category,
                            material,belongs,rating,publish_time,sales,satisfaction_rate,main_img,sub_img,merchant_name,address,payment_method
                            FROM merchant_merchandise_view
                            where merchandise_id=#{merchandiseId}
            """)
    ViewMerchandiseEntity getMerchandiseById(@Param("merchandiseId") Integer merchandiseId);

    @Delete("""
            delete from merchandise where merchandise_id = #{merchandiseId}
            """)
    Integer deleteMerchandise(@Param("merchandiseId") Integer merchandiseId);


    @Select("""
            select * from category
            """)
    List<CategoryEntity> queryAllCategory(@Param("size") Short size, @Param("offset") Integer offset);

    @Select("""
            select category_id from category where category_name = #{name}
            """)
    Integer queryCategoryByName(@Param("name")String name);

    @Select("""
            select * from material
            """)
    List<MaterialEntity> queryAllMaterial();

    @Select("""
            select material_id from material where material_name = #{name}
            """)
    Integer queryMaterialByMaterialName(@Param("name")String name);

    @Delete("""
            delete from category where category_id=#{id}
            """)
    Integer deleteCategoryById(@Param("id") Integer categoryId);

    @Delete("""
            delete from material where material_id=#{id}
            """)
    Integer deleteMaterialById(@Param("id") Integer materialId);

    @Insert("""
            insert category(category_name,alias) values(#{cname},#{alias})
            """)
    Integer insertCategory(@Param("cname") String cname,@Param("alias") String alias);

    @Insert("""
            insert material(material_name,material_description,reconstruction_coefficient,alias)
             values(#{materialName},#{materialDescription},#{reconstructionCoefficient},#{alias})
            """)
    Integer insertMaterial(@Param("materialName") String materialName,
                           @Param("materialDescription") String materialDescription,
                           @Param("reconstructionCoefficient") String reconstructionCoefficient,
                           @Param("alias") String alias);

    @Update("""
            update material set material_name=#{materialName},material_description=#{materialDescription},
            reconstruction_coefficient=#{reconstructionCoefficient},alias=#{alias} where material_id=#{mid}
            """)
    Integer updateMaterialById(@Param("mid") Integer materialId,
                               @Param("materialName") String materialName,
                               @Param("materialDescription") String materialDescription,
                               @Param("reconstructionCoefficient") String reconstructionCoefficient,
                               @Param("alias") String alias);

    @Update("""
            update category set category_name=#{categoryName},alias=#{alias} where category_id=#{categoryId}
            """)
            Integer updateCategoryById(@Param("categoryId")Integer categoryId,
                                       @Param("alias")String alias,
                                       @Param("categoryName")String categoryName);
    // 使用sql生成器编写sql语句

    // 使用xml文件编写sql语句
    List<ViewMerchandiseEntity> getMerchandise(@Param("merchandiseWrapper") MerchandiseWrapper merchandiseWrapper);
    Integer getMerchandiseCount(@Param("merchandiseWrapper") MerchandiseWrapper merchandiseWrapper);
    Integer insertMerchandise(@Param("merchandiseMap") Map<String, String> map);

    Integer updateMerchandise(@Param("merchandiseMap") Map<String, String> map);
}
