package com.yyh.friend.dao;

import com.yyh.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description: 交友模块的dao层
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {

    /**
     * @param friendid
     * @return com.yyh.friend.pojo.NoFriend
     * @Author Tong
     * @Description 通过当前用户和操作的用户查找
     * @Date 2021/4/11
     * @Param userid
     */
    NoFriend findByUseridAndFriendid(String userid, String friendid);
}
