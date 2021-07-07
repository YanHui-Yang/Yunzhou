package com.yyh.qa.controller;

import java.util.Map;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yyh.qa.pojo.Reply;
import com.yyh.qa.service.ReplyService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping("/reply")
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/reply/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/reply/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/reply/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param reply
     */
    @PostMapping("/reply")
    public Result add(@RequestBody Reply reply) {
        replyService.add(reply);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param reply
     */
    @PutMapping("/reply/{id}")
    public Result update(@RequestBody Reply reply, @PathVariable String id) {
        reply.setId(id);
        replyService.update(reply);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/reply/{id}")
    public Result delete(@PathVariable String id) {
        replyService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
