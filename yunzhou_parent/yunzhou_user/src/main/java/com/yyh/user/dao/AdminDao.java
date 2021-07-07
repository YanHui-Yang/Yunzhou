package com.yyh.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yyh.user.pojo.Admin;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    /**
     * @Author Tong
     * @Description 根据登录的用户名查询管理员对象
     * @Date 2021/4/8
     * @Param loginname
     * @return com.yyh.user.pojo.Admin
     */
    Admin findByLoginname(String loginname);
}
