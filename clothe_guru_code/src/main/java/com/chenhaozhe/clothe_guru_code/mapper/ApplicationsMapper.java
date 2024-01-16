package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApplicationsMapper {
    @Select("""
            select * from applications where user_id=#{userId}
            """)
    List<ApplicationsEntity> selectApplicationsByUserId(@Param("userId")Long userId);

    @Select("""
            select * from applications
            """)
    List<ApplicationsEntity> selectApplications();

    @Update("""
            update applications set audit_state=#{auditState},audit_feedback=#{auditFeedback}
            where application_id=#{applicationId}
            """)
    Integer UpdateApplicationsById(@Param("auditState")Short auditState,@Param("auditFeedback")String auditFeedback,
                                   @Param("applicationId")Integer applicationId);

    @Insert("""
            insert applications(user_id,merchant_name,register_mesg) values(#{userId},#{merchantName},#{registerMesg})
            """)
    Integer InsertApplicationByUserId(@Param("userId") Long userId,@Param("merchantName") String merchantName,
                                      @Param("registerMesg")String registerMesg);
}
