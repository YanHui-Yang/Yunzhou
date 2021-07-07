package com.yyh.service;

import com.yyh.dao.ArticleDao;
import com.yyh.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Author: Tong
 * Date: 2021/4/4
 * Description: 文章的service
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public void save(Article article) {
        articleDao.save(article);
    }

    /**
     * @param page
     * @param size
     * @return org.springframework.data.domain.Page<com.yyh.pojo.Article>
     * @Author Tong
     * @Description 关键词分页查询
     * @Date 2021/4/4
     * @Param keyword
     */
    public Page<Article> searchPageData(String keyword, int page, int size) {
        //创建分页对象
        Pageable pageAble = PageRequest.of(page - 1, size);

        //调用dao层,进行返回
        return articleDao.findByTitleOrContentLike(keyword, keyword, pageAble);
    }
}
