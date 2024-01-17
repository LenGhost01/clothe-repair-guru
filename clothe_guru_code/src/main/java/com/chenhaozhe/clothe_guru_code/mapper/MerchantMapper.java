package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.util.DynaSQLProviderBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MerchantMapper {

    //常规写法

    //暂时设置一个用户只能注册一个商家账号
    @Select("""
            select merchant_id from user_merchant where user_id=#{userId})
            """)
    Long selectMerchantIdByUserId(@Param("userId")Long userId);

    @Select("""
            select * from merchant where merchant_id = #{merchantId}
            """)
    MerchantEntity selectMerchantByMerchantId(@Param("merchantId")Long merchantId);

    @Delete("""
            delete from merchant where merchant_id=#{merchantId}
            """)
    Integer deleteMerchantByMerchantId(@Param("merchantId")Long merchantId);

    Integer insertMerchant(Map<String,String> propValueMap);

    // 使用动态sql
    @SelectProvider(type = DynaSQLProviderBuilder.class,method = "getAllMerchant")
    List<MerchantEntity> selectAllMerchant(@Param("limit")Short limit,
                                           @Param("offset")Integer offset,
                                           @Param("keyWord")String keyWord,
                                           @Param("keyState")String keyState,
                                           @Param("orderValue")String orderValue,
                                           @Param("orderRule")Boolean orderRule);


}
