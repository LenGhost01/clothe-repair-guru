package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.AdminEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    @Select("""
            select * from admin where admin_identity=#{adminIdentity}
            """)
    AdminEntity getAdmin(@Param("adminIdentity") String adminIdentity);
}
