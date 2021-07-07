package com.yyh.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yyh.article.dao.ArticleDao;
import com.yyh.article.pojo.Article;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    public Page<Article> pageQuery(String name, int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return articleDao.findAll(searchMap(name), pageAble);
    }

    private Specification<Article> searchMap(String name) {
        return (Specification<Article>) (root, query, cb) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (name == null) {
                return cb.and(new Predicate[list.size()]);
            }
            if (name != null && !"".equals(name)) {
                Predicate predicate = cb.like(root.get("name").as(String.class), "%" + name + "%");
                list.add(predicate);
            }
            Predicate[] parr = new Predicate[list.size()];
            list.toArray(parr);
            return cb.and(parr);
        };
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article) {
        redisTemplate.delete("article_" + article.getId());
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        redisTemplate.delete("article_" + id);
        articleDao.deleteById(id);
    }

    public void updateState(String id) {
        articleDao.updateState(id);
    }

    public void addThumbup(String id) {
        articleDao.addThumbup(id);
    }

    public int deleteBatch(List<String> ids) {
        return articleDao.deleteLabelById(ids);
    }
}
