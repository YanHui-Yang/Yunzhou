package com.yyh.recruit.service;

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
import org.springframework.stereotype.Service;

import com.yyh.recruit.dao.EnterpriseDao;
import com.yyh.recruit.pojo.Enterprise;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    public Page<Enterprise> pageQuery(String name,  int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return enterpriseDao.findAll(searchMap(name), pageAble);
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }

//    private Specification<Enterprise> createSpecification(Map searchMap) {
//
//        return new Specification<Enterprise>() {
//
//            @Override
//            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicateList = new ArrayList<Predicate>();
//                // ID
//                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
//                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
//                }
//                // 企业名称
//                if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
//                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
//                }
//                // 企业简介
//                if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
//                    predicateList.add(cb.like(root.get("summary").as(String.class), "%" + (String) searchMap.get("summary") + "%"));
//                }
//                // 企业地址
//                if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
//                    predicateList.add(cb.like(root.get("address").as(String.class), "%" + (String) searchMap.get("address") + "%"));
//                }
//                // 标签列表
//                if (searchMap.get("labels") != null && !"".equals(searchMap.get("labels"))) {
//                    predicateList.add(cb.like(root.get("labels").as(String.class), "%" + (String) searchMap.get("labels") + "%"));
//                }
//                // 坐标
//                if (searchMap.get("coordinate") != null && !"".equals(searchMap.get("coordinate"))) {
//                    predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + (String) searchMap.get("coordinate") + "%"));
//                }
//                // 是否热门
//                if (searchMap.get("ishot") != null && !"".equals(searchMap.get("ishot"))) {
//                    predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + (String) searchMap.get("ishot") + "%"));
//                }
//                // LOGO
//                if (searchMap.get("logo") != null && !"".equals(searchMap.get("logo"))) {
//                    predicateList.add(cb.like(root.get("logo").as(String.class), "%" + (String) searchMap.get("logo") + "%"));
//                }
//                // URL
//                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
//                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
//                }
//
//                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
//
//            }
//        };
//    }

    private Specification<Enterprise> searchMap(String name) {
        return (Specification<Enterprise>) (root, query, cb) -> {
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

    public List<Enterprise> hotList(String ishot) {
        return enterpriseDao.findByIshot(ishot);
    }

    public Long countByIshot(String ishot) {
        return enterpriseDao.countByIshot(ishot);
    }

    public int deleteBatch(List<String> ids) {
        return enterpriseDao.deleteEnterpriseById(ids);
    }
}
