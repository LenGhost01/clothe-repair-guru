package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseInsertDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseUploadDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseAndCountVo;
import com.chenhaozhe.clothe_guru_code.services.MerchandiseServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/merchandise")
public class MerchandiseController {
    // 用于对商品请求的查询，包括类别和材质
    @Resource
    private MerchandiseServices merchandiseServices;

    //TODO 获取商品的集合，要求：能对商品的关键字进行筛选，根据商家位置的远近进行排序，根据折扣率，发布时间，销量，用户满意度。进行排序，还需
    // 实现针对用户选择的价格区间进行排序的手段 用户查看商品是查看商品视图
    @PostMapping("/getMerchandiseView")
    public MerchandiseAndCountVo getMerchandise(@RequestBody MerchandiseGetterDTO merchandiseGetterDTO) {
        // 最后要转化成vo的格式
        return merchandiseServices.getMerchandise(merchandiseGetterDTO);
    }

    //TODO 获取具体商品的信息并显示在页面中。
    @GetMapping("/getMerchandiseViewById")
    public void getMerchandiseById(@RequestParam("merchandiseId") Integer merchandiseId) {
        // 最后要转化成vo的格式
        merchandiseServices.getMerchandiseById(merchandiseId);
    }

    //TODO 删除特定的商品，用户和管理员使用同一种类型
    @GetMapping("/deleteMerchandise")
    public void deleteMerchandise(@RequestParam("merchandiseId") Integer merchandiseId) {
        merchandiseServices.deleteMerchandise(merchandiseId);
    }

    //TODO 更改商品的所有属性，只能由商家来修改
    @GetMapping("/updateMerchandise")
    public void updateMerchandise(@RequestPart("mainImg") MultipartFile mainImg,
                                  @RequestPart("subImg") MultipartFile[] subImg,
                                  @RequestParam("MerchandiseUpdateMsg") MerchandiseUploadDTO merchandiseUploadDTO) {
        merchandiseServices.updateMerchandise(mainImg, subImg, merchandiseUploadDTO);
    }

    //TODO 添加新商品，只能由商家添加
    @GetMapping("/addMerchandise")
    public void addMerchandise(@RequestPart("mainImg") MultipartFile mainImg,
                               @RequestPart("subImg") MultipartFile[] subImg,
                               @RequestParam("MerchandiseUpdateMsg") MerchandiseInsertDTO merchandiseInsertDTO) {
        merchandiseServices.insertNewMerchandise(mainImg, subImg, merchandiseInsertDTO);
    }

    //TODO 管理员 进行类别的增删查
    @GetMapping("/getCategory")
    public List<CategoryEntity> getCategory(@RequestParam("page") Integer page) {
        return merchandiseServices.getCategory(page);
    }

    @GetMapping("/addCategory")
    public Integer addCategory(@RequestParam("cname") String cname,
                            @RequestParam("alias") String alias) {
        return merchandiseServices.addCategory(cname,alias);
    }

    @GetMapping("/deleteCategory")
    public void deleteCategory(@RequestParam("cid") Integer cid) {
        merchandiseServices.deleteCategoryById(cid);
    }

    //TODO 管理员 进行材质的增删查
    @GetMapping("/getMaterial")
    public List<MaterialEntity> getMaterial(@RequestParam("page") Integer page) {
        return merchandiseServices.getMaterial(page);
    }

    @GetMapping("/addMaterial")
    public Integer addMaterial(@RequestParam("materialName") String materialName,
                            @RequestParam("materialDesc") String materialDesc,
                            @RequestParam("reconstruct") String reconstruct,
                            @RequestParam("alias")String alias) {
       return merchandiseServices.addMaterial(materialName, materialDesc, reconstruct,alias);
    }

    @GetMapping("/deleteMaterial")
    public void deleteMaterial(@RequestParam("mid") Integer mid) {
        merchandiseServices.deleteMaterialById(mid);
    }

    @GetMapping("/updateMaterial")
    public void updateMaterial(@RequestParam("mid") Integer mid,
                               @RequestParam("materialName") String materialName,
                               @RequestParam("materialDesc") String materialDesc,
                               @RequestParam("reconstruct") String reconstruct,
                               @RequestParam("alias") String alias) {
        merchandiseServices.updateMaterial(mid, materialName, materialDesc, reconstruct,alias);
    }

    @GetMapping("/updateCategory")
    public void updateCategory(@RequestParam("cid") Integer cid,
                               @RequestParam("cName") String cName,
                               @RequestParam("alias") String alias){
        merchandiseServices.updateCategory(cid,cName,alias);
    }
}
