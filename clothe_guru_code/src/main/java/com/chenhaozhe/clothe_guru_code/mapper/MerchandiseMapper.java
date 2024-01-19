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
            select * from category limit #{size} offset #{offset}
            """)
    List<CategoryEntity> queryAllCategory(@Param("size") Short size, @Param("offset") Integer offset);

    @Select("""
            select * from material limit #{size} offset #{offset}
            """)
    List<MaterialEntity> queryAllMaterial(@Param("size") Short size, @Param("offset") Integer offset);

    @Delete("""
            delete from category where category_id=#{id}
            """)
    Integer deleteCategoryById(@Param("id") Integer categoryId);

    @Delete("""
            delete from material where material_id=#{id}
            """)
    Integer deleteMaterialById(@Param("id") Integer materialId);

    @Insert("""
            insert category(category_name) values(#{cname})
            """)
    Integer insertCategory(@Param("cname") String cname);

    @Insert("""
            insert material(material_name,material_description,reconstruction_coefficient)
             values(#{materialName},#{materialDescription},#{reconstructionCoefficient})
            """)
    Integer insertMaterial(@Param("materialName") String materialName,
                           @Param("materialDescription") String materialDescription,
                           @Param("reconstructionCoefficient") BigDecimal reconstructionCoefficient);

    @Update("""
            update material set material_name=#{materialName},material_description=#{materialDescription},
            reconstruction_coefficient=#{reconstructionCoefficient} where material_id=#{mid}
            """)
    Integer updateMaterialById(@Param("mid") Integer materialId,
                               @Param("materialName") String materialName,
                               @Param("materialDescription") String materialDescription,
                               @Param("reconstructionCoefficient") BigDecimal reconstructionCoefficient);
    // 使用sql生成器编写sql语句

    // 使用xml文件编写sql语句
    List<ViewMerchandiseEntity> getMerchandise(@Param("merchandiseWrapper") MerchandiseWrapper merchandiseWrapper);

    Integer insertMerchandise(@Param("merchandiseMap") Map<String, String> map);

    Integer updateMerchandise(@Param("merchandiseMap") Map<String, String> map);
}
