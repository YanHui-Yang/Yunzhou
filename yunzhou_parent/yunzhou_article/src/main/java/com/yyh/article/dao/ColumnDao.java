package com.yyh.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yyh.article.pojo.Column;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ColumnDao extends JpaRepository<Column, String>, JpaSpecificationExecutor<Column> {

    @Modifying
    @Query(value = "delete from tb_column where id in (:ids)", nativeQuery = true)
    int deleteLabelById(@Param("ids") List<String> ids);
}
