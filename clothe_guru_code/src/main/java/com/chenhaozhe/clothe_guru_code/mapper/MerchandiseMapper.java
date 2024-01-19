package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.ViewMerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.wrapper.MerchandiseWrapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
    ViewMerchandiseEntity getMerchandiseById(@Param("merchandiseId")Integer merchandiseId);

    @Delete("""
            delete from merchandise where merchandise_id = #{merchandiseId}
            """)
    Integer deleteMerchandise(@Param("merchandiseId")Integer merchandiseId);
    // 使用sql生成器编写sql与刺激

    // 使用xml文件编写sql语句
    List<ViewMerchandiseEntity> getMerchandise(@Param("merchandiseWrapper")MerchandiseWrapper merchandiseWrapper);

    Integer insertMerchandise(@Param("merchandiseMap")Map map);

    Integer updateMerchandise(@Param("merchandiseMap")Map map);
}
