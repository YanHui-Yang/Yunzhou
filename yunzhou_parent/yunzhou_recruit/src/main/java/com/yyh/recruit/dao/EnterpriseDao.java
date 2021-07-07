package com.yyh.recruit.dao;

import com.yyh.recruit.pojo.Enterprise;
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
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

    /**
     * @return java.util.List<com.yyh.recruit.pojo.Enterprise>
     * @Author Tong
     * @Description 查找热门企业  findByIshot 相当于sql  where isHot=?
     * @Date 2021/4/1
     * @Param ishot
     */
    List<Enterprise> findByIshot(String ishot);

    Long countByIshot(String ishot);

    @Modifying
    @Query(value = "delete from tb_enterprise where id in (:ids)", nativeQuery = true)
    int deleteEnterpriseById(@Param("ids") List<String> ids);
}
