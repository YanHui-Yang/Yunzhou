package com.yyh.friend.dao;

import com.yyh.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description:
 */
public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * @param friendid
     * @return com.yyh.friend.pojo.Friend
     * @Author Tong
     * @Description 通过当前用户和操作的用户查找
     * @Date 2021/4/11
     * @Param userid
     */
    Friend findByUseridAndFriendid(String userid, String friendid);

    /**
     * @param userid
     * @param friendid
     * @return void
     * @Author Tong
     * @Description 更新喜欢的状态
     * @Date 2021/4/11
     * @Param islike
     */
    @Modifying
    @Query(value = "update  tb_friend set islike=? where userid=? and friendid = ?", nativeQuery = true)
    void updateIsLike(String islike, String userid, String friendid);

    /**
     * @param friendid
     * @return void
     * @Author Tong
     * @Description 删除好友表中的信息
     * @Date 2021/4/11
     * @Param userid
     */
    @Modifying
    @Query(value = "delete  from  tb_friend where userid=? and friendid = ?", nativeQuery = true)
    void deleteFriend(String userid, String friendid);
}
