package com.yyh.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.user.pojo.LoginUser;
import com.yyh.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yyh.user.pojo.Admin;
import com.yyh.user.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    private String accessToken;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(StatusCode.OK, "查询成功", adminService.findAll(), adminService.countAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Result findById(@PathVariable String id) {
//        return new Result(StatusCode.OK, "查询成功", adminService.findById(id));
//    }


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
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result(StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()), adminService.countAll());
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(StatusCode.OK, "查询成功", adminService.findSearch(searchMap), adminService.countAll());
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        adminService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody LoginUser user) {
        Admin admin = new Admin();
        admin.setLoginname(user.getUsername());
        admin.setPassword(user.getPassword());
        //从service层获取admin
        admin = adminService.login(admin);
        if (admin == null) {
            //未查询到用户,提示前端登录失败
            return new Result(StatusCode.LOGINERROR, "登录失败");
        }
        //使得前后端可以知道登录状态的操作,采用jwt生成令牌
        //生成令牌,传递三个参数, 用户的id,登录名,角色信息
        this.accessToken = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
        HashMap<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("role", "admin");
        map.put("permissions", "admin");
        //使得前后端可以知道登录状态的操作
        return new Result(StatusCode.OK, "登录成功", map, adminService.countAll());
    }

    @GetMapping("info")
    public Result info() {
        System.out.println(this.accessToken);
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles", "admin");
        map.put("ability", new String[] { "READ", "WRITE", "DELETE" });
        map.put("username", "admin");
        map.put("permissions", list);
        map.put("avatar", "https://i.gtimg.cn/club/item/face/img/8/15918_100.gif");
        return new Result(StatusCode.OK, "登录成功", map, adminService.countAll());
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userInfo");
        session.removeAttribute("role_id");
        return new Result(StatusCode.OK, "退出成功");
    }

}
