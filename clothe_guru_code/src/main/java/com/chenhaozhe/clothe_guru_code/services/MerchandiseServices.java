package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseUploadDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseAndCountVo;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseVo;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface MerchandiseServices {
    MerchandiseAndCountVo getMerchandise(MerchandiseGetterDTO merchandiseGetterDTO);

    MerchandiseAndCountVo getMerchandiseById(Long merchandiseId, String keyword, Integer page);

    Integer deleteMerchandise(Integer merchandiseId);

    void insertNewMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseInsertDTO merchandiseInsertDTO);

    void updateMerchandise(MultipartFile mainImg, MultipartFile[] subImg, MerchandiseUploadDTO merchandiseUploadDTO);

    MerchandiseVo getMerchandiseUnitById(Integer id);
}
