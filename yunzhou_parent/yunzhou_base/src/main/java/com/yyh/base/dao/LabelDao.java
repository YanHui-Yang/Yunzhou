package com.yyh.base.dao;

import com.yyh.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Author: Tong
 * Date: 2021/3/30
 * Description: 标签的dao层接口
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {

    @Modifying
    @Query(value = "delete from tb_label where id in (:ids)", nativeQuery = true)
    int deleteLabelById(@Param("ids")List<String> ids);
}
