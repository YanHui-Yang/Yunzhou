package com.yyh.spit.service;

import com.yyh.spit.dao.SpitDao;
import com.yyh.spit.pojo.Spit;
import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: Tong
 * Date: 2021/4/2
 * Description: 吐槽微服务的service层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void saveSpit(Spit spit) {
        spit.set_id(String.valueOf(idWorker.nextId()));

        //初始化数据
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态

        //判断当前新增的吐槽,有无父节点, 如果有父节点,那么其对应的父节点要加一
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            //把其父节点的回复数进行自增加一的操作
            Query query = new Query();
            //查询条件为父节点的id
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            //把父节点的回复数进行加一的操作
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteSpitById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    /**
     * @return void
     * @Author Tong
     * @Description 点赞
     * @Date 2021/4/2
     * @Param spitId
     */
    public void thumbup(String spitId) {
        //用于指定查询的条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));

        //用于指定在哪个字段上进行自增
        Update update = new Update();
        update.inc("thumbup", 1);
        /**
         * 传递三个参数
         * query 对象,用于指定查询的条件
         * update 对象 用于指定在哪个字段上进行自增
         * collectionName 第三个参数,用于指定在哪个集合即哪张表上进行自增
         */
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
