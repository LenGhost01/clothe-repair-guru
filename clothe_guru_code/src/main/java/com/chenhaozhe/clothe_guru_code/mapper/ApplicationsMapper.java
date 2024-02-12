package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ApplicationsMapper {
    @Select("""
            select * from applications_users_view where user_id=#{userId} limit #{size} offset #{offset}
            """)
    List<ApplicationsEntity> selectApplicationsByUserId(@Param("userId")Long userId,@Param("size")Short size,
                                                        @Param("offset")Integer offset);

    @Select("""
            select count(application_id) from applications_users_view where user_id = #{userId}
            """)
    Integer selectUsersApplicationsCount(@Param("userId")Long userId);

    @Select("""
            select * from applications_users_view limit #{size} offset #{offset}
            """)
    List<ApplicationsEntity> selectApplications(@Param("size")Short size, @Param("offset")Integer offset);
    @Select("""
            select count(application_id) from applications_users_view
            """)
    Integer selectApplicationsCount();
    @Select("""
            select * from applications_users_view ${keyState} like #{keyWord} limit #{size} offset #{offset}
            """)
    List<ApplicationsEntity> selectApplicationsLikeKeyWord(@Param("size")Short size, @Param("offset")Integer offset,
                                                @Param("keyState")String keyState,@Param("KeyWord")String keyWord);


    @Update("""
            update applications set audit_state=#{auditState}
            where application_id=#{applicationId}
            """)
    Integer UpdateApplicationsById(@Param("auditState")Short auditState,
                                   @Param("applicationId")Integer applicationId);

    @Update("{CALL update_application_state(#{input_application_id}, #{input_user_id}, #{input_merchant_id}, #{input_merchant_name}, #{input_contact}, #{input_address}, #{input_merchant_description}, #{input_audit_state}, #{input_certification})}")
    Integer updateApplicationState(
            @Param("input_application_id") Integer inputApplicationId,
            @Param("input_user_id") Long inputUserId,
            @Param("input_merchant_id") Long inputMerchantId,
            @Param("input_merchant_name") String inputMerchantName,
            @Param("input_contact") String inputContact,
            @Param("input_email") String inputEmail,
            @Param("input_address") String inputAddress,
            @Param("input_merchant_description") String inputMerchantDescription,
            @Param("input_audit_state") Short inputAuditState,
            @Param("input_certification") String inputCertification
    );
    // 使用xml文件
    Integer InsertApplicationByUserId(@Param("applicationsMap") Map<String,String> applicationsMap);
}
