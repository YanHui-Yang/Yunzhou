package com.yyh.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yyh.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    /**
     * @return void
     * @Author Tong
     * @Description 更新文章状态，1审核通过，0未审核
     * @Date 2021/4/1
     * @Param id
     */
    @Modifying
    @Query(value = "update tb_article set state =1 where id=?", nativeQuery = true)
    void updateState(String id);

    /**
     * @return void
     * @Author Tong
     * @Description 点赞
     * @Date 2021/4/1
     * @Param id
     */
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup+1 where id=?", nativeQuery = true)
    void addThumbup(String id);

    @Modifying
    @Query(value = "delete from tb_article where id in (:ids)", nativeQuery = true)
    int deleteLabelById(@Param("ids") List<String> ids);
}
