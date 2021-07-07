package com.yyh.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yyh.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * @param pageable
     * @return org.springframework.data.domain.Page<com.yyh.qa.pojo.Problem>
     * @Author Tong
     * @Description 最新问题列表
     * @Date 2021/4/1
     * @Param labelid
     */
    @Query(value = "select * from tb_problem a ,tb_pl b WHERE a.id = b.problemid and b.labelid=? ORDER BY a.replytime desc", nativeQuery = true)
    Page<Problem> newList(String labelid, Pageable pageable);

    /**
     * @param pageable
     * @return org.springframework.data.domain.Page<com.yyh.qa.pojo.Problem>
     * @Author Tong
     * @Description 热门问题列表. 回复数最多的
     * @Date 2021/4/1
     * @Param labelid
     */
    @Query(value = "select * from tb_problem a ,tb_pl b WHERE a.id = b.problemid and b.labelid=? ORDER BY a.reply desc", nativeQuery = true)
    Page<Problem> hotList(String labelid, Pageable pageable);

    /**
     * @param pageable
     * @return org.springframework.data.domain.Page<com.yyh.qa.pojo.Problem>
     * @Author Tong
     * @Description 等待问题列表. 回复数为0的
     * @Date 2021/4/1
     * @Param labelid
     */
    @Query(value = "select * from tb_problem a ,tb_pl b WHERE a.id = b.problemid and b.labelid=? and a.reply=0 ORDER BY a.createtime desc", nativeQuery = true)
    Page<Problem> waitList(String labelid, Pageable pageable);
}
