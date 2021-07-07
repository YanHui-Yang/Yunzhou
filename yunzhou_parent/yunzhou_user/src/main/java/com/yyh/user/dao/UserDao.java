package com.yyh.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yyh.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByMobile(String mobile);

    /**
     * @param friendid
     * @return void
     * @Author Tong
     * @Description 修改粉丝的数量信息
     * @Date 2021/4/11
     * @Param type 数量加一或者减一
     */
    @Modifying
    @Query(value = "update tb_user set fanscount=fanscount+? where id=?", nativeQuery = true)
    void updateFanscount(int type, String friendid);

    /**
     * @param userid
     * @return void
     * @Author Tong
     * @Description 修改关注的数量信息
     * @Date 2021/4/11
     * @Param type 数量加一或者减一
     */
    @Modifying
    @Query(value = "update tb_user set followcount=followcount+? where id=?", nativeQuery = true)
    void updateFollowcount(int type, String userid);
}
