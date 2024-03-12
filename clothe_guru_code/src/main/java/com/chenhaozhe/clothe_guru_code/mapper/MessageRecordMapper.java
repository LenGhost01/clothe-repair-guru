package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.ChannelMessageRecordEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.PeerMessageRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageRecordMapper {

    List<ChannelMessageRecordEntity> getChannelMessage(String userId, String targetId);
    void addChannelMessage(ChannelMessageRecordEntity messageRecord);

    // 使用xml文件
    List<PeerMessageRecordEntity> getPeerMessage(@Param("members") List<String> members);
    Integer addPeerMessage(@Param("messages") List<PeerMessageRecordEntity> messageRecord);

}


