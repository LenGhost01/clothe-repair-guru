package com.chenhaozhe.clothe_guru_code;

import com.chenhaozhe.clothe_guru_code.model.entity.MerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.util.ClassPropertyValueMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

@SpringBootTest
@Slf4j
class ClotheGuruCodeApplicationTests {


    @Test
    void autoTrans() throws IllegalAccessException {
        MerchandiseEntity build = MerchandiseEntity.builder()
                .lowPrice(new BigDecimal(12.625))
                .highPrice(new BigDecimal(127.5))
                .build();

        Map<String, String> propertyValueMapListSnackCase = ClassPropertyValueMap.getPropertyValueMapListSnackCase(build);
        log.info(propertyValueMapListSnackCase.entrySet().toString());
        log.info(propertyValueMapListSnackCase.values().toString());
    }

}
