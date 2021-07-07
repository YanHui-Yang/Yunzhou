package com.yyh.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.yyh.article.pojo.Article;
import com.yyh.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yyh.article.dao.ChannelDao;
import com.yyh.article.pojo.Channel;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private IdWorker idWorker;

    public Page<Channel> pageQuery(String name, int pageNo, int pageSize) {
        Pageable pageAble = PageRequest.of(pageNo - 1, pageSize);
        return channelDao.findAll(searchMap(name), pageAble);
    }

    private Specification<Channel> searchMap(String name) {
        return (Specification<Channel>) (root, query, cb) -> {
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
     * @param channel
     */
    public void add(Channel channel) {
        channel.setId(idWorker.nextId() + "");
        channelDao.save(channel);
    }

    /**
     * 修改
     *
     * @param channel
     */
    public void update(Channel channel) {
        channelDao.save(channel);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        channelDao.deleteById(id);
    }

    public Long countAll() {
        return channelDao.count();
    }

    public int deleteBatch(List<String> ids) {
        return channelDao.deleteLabelById(ids);
    }

}
