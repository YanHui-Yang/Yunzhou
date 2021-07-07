package com.yyh.recruit.dao;

import com.yyh.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

    /**
     * @return java.util.List<com.yyh.recruit.pojo.Recruit>
     * @Author Tong
     * @Description 查询最新的职位  top6 代表只查询前6个
     * @Date 2021/4/1
     * @Param state
     */
    List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    /**
     * @Author Tong
     * @Description 查询前6个最新的职位,按照时间倒序排序
     * @Date 2021/4/1
     * @Param state
     * @return java.util.List<com.yyh.recruit.pojo.Recruit>
     */
    List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);

    @Modifying
    @Query(value = "delete from tb_recruit where id in (:ids)", nativeQuery = true)
    int deleteRecruitById(@Param("ids") List<String> ids);
}
