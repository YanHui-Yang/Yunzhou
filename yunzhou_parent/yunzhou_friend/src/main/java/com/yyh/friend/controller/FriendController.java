package com.yyh.friend.controller;

import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.friend.client.UserClient;
import com.yyh.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description: 交友业务的Controller层
 */
@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims = Jwts.parser().setSigningKey("yunzhou") //指定的密钥
                .parseClaimsJws((String) request.getAttribute("claims_user"))
                .getBody();
        if (claims == null) {
            //说明当前用户没有user角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //得到当前登录的用户id
        String userId = claims.getId();

        //判断是添加好友,还是添加非好友
        if (type != null && !"".equals(type)) {

            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(userId, friendid);

                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
                if (flag == 1) {
                    userClient.updateFanscountAndFollowcount(1, userId, friendid);
                    return new Result(true, StatusCode.OK, "添加成功");
                }

            } else if ("2".equals(type)) {
                //添加非好友
                int flag = friendService.addNoFriend(userId, friendid);

                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                }
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }

            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }

    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result addFriend(@PathVariable String friendid) {
        //验证是否登录, 并且拿到登录用户的id
        Claims claims = Jwts.parser().setSigningKey("yunzhou") //指定的密钥
                .parseClaimsJws((String) request.getAttribute("claims_user"))
                .getBody();
        if (claims == null) {
            //说明当前用户没有user角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //得到当前登录的用户id
        String userId = claims.getId();

        friendService.deleteFriend(userId, friendid);

        //更新粉丝数和关注数
        userClient.updateFanscountAndFollowcount(-1, userId, friendid);
        return new Result(true, StatusCode.OK, "删除好友成功");
    }
}
