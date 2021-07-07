package com.yyh.article.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.yyh.article.pojo.Channel;
import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yyh.article.dao.ColumnDao;
import com.yyh.article.pojo.Column;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ColumnService {

    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private IdWorker idWorker;
    public Page<Column> pageQuery(String name, int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return columnDao.findAll(searchMap(name), pageAble);
    }

    private Specification<Column> searchMap(String name) {
        return (Specification<Column>) (root, query, cb) -> {
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
     * @param column
     */
    public void add(Column column) {
        column.setId(String.valueOf(idWorker.nextId()));
        column.setCreatetime(new Date());
        columnDao.save(column);
    }

    /**
     * 修改
     *
     * @param column
     */
    public void update(Column column) {
        column.setChecktime(new Date());
        columnDao.save(column);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        columnDao.deleteById(id);
    }

    public int deleteBatch(List<String> ids) {
        return columnDao.deleteLabelById(ids);
    }

}
