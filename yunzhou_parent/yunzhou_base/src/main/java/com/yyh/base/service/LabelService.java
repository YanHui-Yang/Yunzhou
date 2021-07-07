package com.yyh.base.service;

import com.yyh.base.dao.LabelDao;
import com.yyh.base.pojo.Label;
import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tong
 * Date: 2021/3/30
 * Description: 标签的service层
 */
@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * @return org.springframework.data.domain.Page<com.yyh.base.pojo.Label>
     * @Author Tong
     * @Description 分页查询
     * @Date 2021/4/1
     * @Param label
     */
    public Page<Label> pageQuery(String labelname, int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return labelDao.findAll(searchMap(labelname), pageAble);
    }

    /**
     * @return org.springframework.data.jpa.domain.Specification<com.yyh.base.pojo.Label>
     * @Author Tong
     * @Description 封装查询对象
     * @Date 2021/4/1
     * @Param label
     */
    private Specification<Label> searchMap(String labelname) {
        return (Specification<Label>) (root, query, cb) -> {
            //存放一个集合, 用于存储所有的条件
            ArrayList<Predicate> list = new ArrayList<>();
            if (labelname != null && !"".equals(labelname)) {
                // 根据标签的名称,模糊查询
                Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + labelname + "%");
                list.add(predicate);
            }
            //创建一个数组, 作为最终的返回值的条件
            Predicate[] parr = new Predicate[list.size()];
            //把list转为数组
            list.toArray(parr);
            //返回所有的条件
            return cb.and(parr);
        };
    }

    public void save(Label label) {
        label.setId(String.valueOf(idWorker.nextId()));
        label.setCount(0L);
        label.setFans(0L);
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public int deleteBatch(List<String> ids) {
        return labelDao.deleteLabelById(ids);
    }

}
