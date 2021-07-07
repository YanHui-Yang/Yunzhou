package com.yyh.recruit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.yyh.recruit.pojo.Enterprise;
import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yyh.recruit.dao.RecruitDao;
import com.yyh.recruit.pojo.Recruit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class RecruitService {

    // 推荐职位的值为2
    private static final String RECOMMED = "2";
    // 关闭职位的值为0
    private static final String CLOSEJOB = "0";

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Recruit> findAll() {
        return recruitDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @return
     */
    public Page<Recruit> pageQuery(String jobname, int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return recruitDao.findAll(searchMap(jobname), pageAble);
    }

    /**
     * 增加
     *
     * @param recruit
     */
    public void add(Recruit recruit) {
        recruit.setId(String.valueOf(idWorker.nextId()));
        recruit.setCreatetime(new Date());
        recruitDao.save(recruit);
    }

    /**
     * 修改
     *
     * @param recruit
     */
    public void update(Recruit recruit) {
        recruitDao.save(recruit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        recruitDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @return
     */
//    private Specification<Recruit> createSpecification(Map searchMap) {
//
//        return new Specification<Recruit>() {
//
//            @Override
//            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicateList = new ArrayList<Predicate>();
//                // ID
//                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
//                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
//                }
//                // 职位名称
//                if (searchMap.get("jobname") != null && !"".equals(searchMap.get("jobname"))) {
//                    predicateList.add(cb.like(root.get("jobname").as(String.class), "%" + (String) searchMap.get("jobname") + "%"));
//                }
//                // 薪资范围
//                if (searchMap.get("salary") != null && !"".equals(searchMap.get("salary"))) {
//                    predicateList.add(cb.like(root.get("salary").as(String.class), "%" + (String) searchMap.get("salary") + "%"));
//                }
//                // 经验要求
//                if (searchMap.get("condition") != null && !"".equals(searchMap.get("condition"))) {
//                    predicateList.add(cb.like(root.get("condition").as(String.class), "%" + (String) searchMap.get("condition") + "%"));
//                }
//                // 学历要求
//                if (searchMap.get("education") != null && !"".equals(searchMap.get("education"))) {
//                    predicateList.add(cb.like(root.get("education").as(String.class), "%" + (String) searchMap.get("education") + "%"));
//                }
//                // 任职方式
//                if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
//                    predicateList.add(cb.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
//                }
//                // 办公地址
//                if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
//                    predicateList.add(cb.like(root.get("address").as(String.class), "%" + (String) searchMap.get("address") + "%"));
//                }
//                // 企业ID
//                if (searchMap.get("eid") != null && !"".equals(searchMap.get("eid"))) {
//                    predicateList.add(cb.like(root.get("eid").as(String.class), "%" + (String) searchMap.get("eid") + "%"));
//                }
//                // 状态
//                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
//                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
//                }
//                // 网址
//                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
//                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
//                }
//                // 标签
//                if (searchMap.get("label") != null && !"".equals(searchMap.get("label"))) {
//                    predicateList.add(cb.like(root.get("label").as(String.class), "%" + (String) searchMap.get("label") + "%"));
//                }
//                // 职位描述
//                if (searchMap.get("content1") != null && !"".equals(searchMap.get("content1"))) {
//                    predicateList.add(cb.like(root.get("content1").as(String.class), "%" + (String) searchMap.get("content1") + "%"));
//                }
//                // 职位要求
//                if (searchMap.get("content2") != null && !"".equals(searchMap.get("content2"))) {
//                    predicateList.add(cb.like(root.get("content2").as(String.class), "%" + (String) searchMap.get("content2") + "%"));
//                }
//
//                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
//            }
//        };
//
//    }

    private Specification<Recruit> searchMap(String jobname) {
        return (Specification<Recruit>) (root, query, cb) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (jobname == null) {
                return cb.and(new Predicate[list.size()]);
            }
            if (jobname != null && !"".equals(jobname)) {
                Predicate predicate = cb.like(root.get("jobname").as(String.class), "%" + jobname + "%");
                list.add(predicate);
            }
            Predicate[] parr = new Predicate[list.size()];
            list.toArray(parr);
            return cb.and(parr);
        };
    }

    public List<Recruit> recommend() {
        return recruitDao.findTop6ByStateOrderByCreatetimeDesc(RECOMMED);
    }

    public List<Recruit> newlist() {
        return recruitDao.findTop6ByStateNotOrderByCreatetimeDesc(CLOSEJOB);
    }

    public int deleteBatch(List<String> ids) {
        return recruitDao.deleteRecruitById(ids);
    }
}
