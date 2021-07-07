package com.yyh.dao;

import com.yyh.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Author: Tong
 * Date: 2021/4/4
 * Description: 文章的dao层
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    /**
     * @param content
     * @param pageable
     * @return org.springframework.data.domain.Page<com.yyh.pojo.Article>
     * @Author Tong
     * @Description 根据文章的标题 内容 进行模糊分页查询
     * @Date 2021/4/4
     * @Param title
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
