package com.yyh.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.yyh.user.pojo.User;
import com.yyh.user.service.UserService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(StatusCode.OK, "查询成功", userService.findAll(), userService.countAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(StatusCode.OK, "查询成功", userService.findById(id), userService.countAll());
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()), userService.countAll());
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(StatusCode.OK, "查询成功", userService.findSearch(searchMap), userService.countAll());
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(StatusCode.OK, "发送成功");
    }

    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result regist(@PathVariable String code, @RequestBody User user) {
        //获取缓存中的验证码
        String checkCodeRedis = (String) redisTemplate.opsForValue().get("checkCode" + user.getMobile());
        System.out.println(user.getMobile());
        System.out.println(checkCodeRedis);
        if (StringUtils.isEmpty(checkCodeRedis)) {
            //从redis中获取的验证码为空,代表没有先获取验证码
            return new Result(StatusCode.ERROR, "请先获取验证码");
        }
        if (!checkCodeRedis.equals(code)) {
            //如果输入的验证码不正确,那么也直接返回该方法
            return new Result(StatusCode.ERROR, "请输入正确的验证码");
        }
        //验证码正确了,进行保存用户信息的操作
        userService.add(user);
        return new Result(StatusCode.OK, "注册成功");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {
        User user1 = userService.login(user.getMobile(), user.getPassword());
        if (user1 == null) {
            return new Result(StatusCode.LOGINERROR, "登录失败");
        }
        String token = jwtUtil.createJWT(user1.getId(), user1.getMobile(), "user");
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("roles", "user");
        return new Result(StatusCode.OK, "登录成功", map, userService.countAll());
    }

    /**
     * @param userid
     * @param friendid
     * @return void
     * @Author Tong
     * @Description 更新粉丝数和关注数
     * @Date 2021/4/11
     * @Param type
     */
    @RequestMapping(value = "/{userid}/{friendid}/{type}", method = RequestMethod.PUT)
    public void updateFanscountAndFollowcount(@PathVariable int type, @PathVariable String userid, @PathVariable String friendid) {
        userService.updateFanscountAndFollowcount(type, userid, friendid);
    }

}
