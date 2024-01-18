package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.mapper.MerchandiseMapper;
import com.chenhaozhe.clothe_guru_code.mapper.UserMapper;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.ViewMerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchandiseKeyWordLabelEnum;
import com.chenhaozhe.clothe_guru_code.model.enums.MerchandiseOrderLabelEnum;
import com.chenhaozhe.clothe_guru_code.model.wrapper.MerchandiseWrapper;
import com.chenhaozhe.clothe_guru_code.services.MerchandiseServices;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import com.chenhaozhe.clothe_guru_code.util.GenerateFormattedImagePath;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MerchandiseServicesImpl implements MerchandiseServices {
    @Resource
    private MerchandiseMapper merchandiseMapper;

    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;

    @Override
    public void getMerchandise(MerchandiseGetterDTO merchandiseGetterDTO) {
        Integer offset = merchandiseGetterDTO.getPage()*defaultPageSize;
        MerchandiseWrapper merchandiseWrapper = MerchandiseWrapper.builder()
                .keyWord(merchandiseGetterDTO.getKeyWord())
                .keyWordLabel(MerchandiseKeyWordLabelEnum.values()[merchandiseGetterDTO.getKeyWordLabel()].getLabel())
                .orderLabel(MerchandiseOrderLabelEnum.values()[merchandiseGetterDTO.getOrderLabel()].getLabel())
                .orderRule(Objects.equals(merchandiseGetterDTO.getOrderRule(), true) ? "ASC" : "DESC")
                .highPrice(merchandiseGetterDTO.getHighPrice())
                .lowPrice(merchandiseGetterDTO.getLowPrice())
                .pageSize(defaultPageSize)
                .offset(offset)
                .build();
        List<ViewMerchandiseEntity> merchandise = merchandiseMapper.getMerchandise(merchandiseWrapper);
        // 转化成vo

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
    public void insertNewMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseInsertDTO merchandiseInsertDTO) {
        // 对图片进行处理。
        String mainImgPath = GenerateFormattedImagePath.generateNewPath(mainImg.getOriginalFilename());
        List<String> subImgPath = new ArrayList<String>();

    }
}
