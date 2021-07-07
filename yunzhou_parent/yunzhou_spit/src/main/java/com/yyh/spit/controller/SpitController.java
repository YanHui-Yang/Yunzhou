package com.yyh.spit.controller;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.spit.pojo.Spit;
import com.yyh.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Tong
 * Date: 2021/4/2
 * Description: 吐槽模块的controller层
 */
@RestController
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/spit")
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @GetMapping("/spit/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    @PostMapping("/spit")
    public Result save(@RequestBody Spit spit) {
        spitService.saveSpit(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @PutMapping("/spit/{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/spit/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteSpitById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/spit/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentid,
                                 @PathVariable int size,
                                 @PathVariable int page) {
        Page<Spit> pageData = spitService.findByParentId(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 对于重复点赞, 只把用户的id存入了redis的缓存, 作为了key.
     * 那么导致的结果是只能对一个吐槽进行点赞,即使是第一次点赞别的吐槽,也会提示不能重复点赞.
     * 因此要把用户的id和吐槽的id, 共同作为redis的键进行.
     * @Date 2021/4/2
     * @Param spitId
     */
    @PutMapping("/spit/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {
        /*spitService.thumbup(spitId);
        return new Result(true, StatusCode.OK, "点赞成功");*/
        //判断当前的用户是否已经点赞了,由于没有搞登录认证,先把userid写死
        String userId = "111";

        //判断当前用户是否已经点赞了
        if (redisTemplate.opsForValue().get("thumbup_" + userId + spitId) != null) {
            return new Result(false, StatusCode.REMOTEERROR, "不能重复点赞");
        }
        //调用service层,进行点赞
        spitService.thumbup(spitId);
        //如果用户点赞成功,就把点赞的信息,存入redis中
        redisTemplate.opsForValue().set("thumbup_" + userId + spitId, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
