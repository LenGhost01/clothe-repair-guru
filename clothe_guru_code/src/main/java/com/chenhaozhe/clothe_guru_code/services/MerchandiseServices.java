package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface MerchandiseServices {
    void getMerchandise(MerchandiseGetterDTO merchandiseGetterDTO);
    void getMerchandiseById(Integer merchandiseId);
    Integer deleteMerchandise(Integer merchandiseId);
    void insertNewMerchandise(MultipartFile mainImg,MultipartFile[] subImg,MerchandiseInsertDTO merchandiseInsertDTO);
}
