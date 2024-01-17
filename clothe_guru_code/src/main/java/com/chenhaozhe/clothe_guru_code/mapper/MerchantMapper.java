package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.util.DynaSQLProviderBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface MerchantMapper {

    @SelectProvider(type = DynaSQLProviderBuilder.class,method = "getAllMerchant")
    List<MerchantEntity> selectAllMerchant(@Param("limit")Short limit,
                                           @Param("offset")Integer offset,
                                           @Param("keyWord")String keyWord,
                                           @Param("keyState")String keyState,
                                           @Param("orderValue")String orderValue,
                                           @Param("orderRule")Boolean orderRule);

}
