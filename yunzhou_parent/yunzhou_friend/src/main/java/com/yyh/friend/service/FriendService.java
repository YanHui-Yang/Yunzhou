package com.yyh.friend.service;

import com.yyh.friend.dao.FriendDao;
import com.yyh.friend.dao.NoFriendDao;
import com.yyh.friend.pojo.Friend;
import com.yyh.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description: 交友微服务的服务层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * @param friendid
     * @return int
     * @Author Tong
     * @Description 通过当前用户和操作的用户查找
     * @Date 2021/4/11
     * @Param userId
     */
    public int addFriend(String userId, String friendid) {
        //先判断userid到friendid是否有数据, 如果有数据,就代表是重复添加好友,返回0
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendid);
        if (friend != null) {
            //有数据,代表重复添加,返回0
            return 0;
        }
        //直接添加好友, 让好友表中userid到friendid的方向type为0
        //此时的friend为null,因此直接new一个接收
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断从friendid到userid是否有数据,如果有,把双方的状态都改为1
        if (friendDao.findByUseridAndFriendid(friendid, userId) != null) {
            //不为null,代表双方都喜欢了,把双方的islike都改为1
            friendDao.updateIsLike("1", userId, friendid);
            friendDao.updateIsLike("1", friendid, userId);
        }
        return 1;
    }

    public int addNoFriend(String userId, String friendid) {
        //通过数据库查询是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendid);
        if (noFriend != null) {
            //代表是重复添加
            return 0;
        }
        //不是好友
        noFriend = new NoFriend();
        noFriend.setFriendid(friendid);
        noFriend.setUserid(userId);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userId, String friendid) {
        //删除好友表中userid到friendid的数据
        friendDao.deleteFriend(userId, friendid);

        //更新friendid到userid的islike字段为0
        //即更新被关注者的islike字段为0 ,原本可能是1的,但由于对方不喜欢她了,因此要改成0
        //但也可能找不到该字段,为空操作.
        friendDao.updateIsLike("0", friendid, userId);

        //非好友列表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
