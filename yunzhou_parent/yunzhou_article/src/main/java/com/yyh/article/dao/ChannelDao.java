package com.yyh.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yyh.article.pojo.Channel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ChannelDao extends JpaRepository<Channel, String>, JpaSpecificationExecutor<Channel> {

    @Modifying
    @Query(value = "delete from tb_channel where id in (:ids)", nativeQuery = true)
    int deleteLabelById(@Param("ids") List<String> ids);

}
