package com.yyh.gathering.service;

import com.yyh.gathering.dao.GatheringDao;
import com.yyh.gathering.pojo.Gathering;
import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    @Autowired
    private IdWorker idWorker;

    public Page<Gathering> pageQuery(String name, int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return gatheringDao.findAll(searchMap(name), pageAble);
    }

    /**
     * 增加
     *
     * @param gathering
     */
    public void add(Gathering gathering) {
        gathering.setId(String.valueOf(idWorker.nextId()));
        gatheringDao.save(gathering);
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @CacheEvict(value = "gathering",key = "#gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "gathering",key = "#id")
    public void deleteById(String id) {
        gatheringDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @return
     */
    private Specification<Gathering> searchMap(String name) {
        return (Specification<Gathering>) (root, query, cb) -> {
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

    public int deleteBatch(List<String> ids) {
        return gatheringDao.deleteLabelById(ids);
    }

}
