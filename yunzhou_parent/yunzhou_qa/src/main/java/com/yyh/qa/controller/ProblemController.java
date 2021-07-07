package com.yyh.qa.controller;

import java.util.Map;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.qa.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yyh.qa.pojo.Problem;
import com.yyh.qa.service.ProblemService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private BaseClient baseClient;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping("/problem")
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/problem/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/problem/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/problem/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @PostMapping("/problem")
    public Result add(@RequestBody Problem problem) {
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @PutMapping("/problem/{id}")
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/problem/{id}")
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * @param page
     * @param size
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 最新问题列表
     * @Date 2021/4/1
     * @Param labelid
     */
    @GetMapping("/problem/newlist/{labelid}/{page}/{size}")
    public Result newList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.newList(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功!", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * @param page
     * @param size
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 热门问题列表. 回复数最多的
     * @Date 2021/4/1
     * @Param labelid
     */
    @GetMapping("/problem/hotlist/{labelid}/{page}/{size}")
    public Result hotlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.hotList(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功!", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * @param page
     * @param size
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 等待问题列表. 回复数为0的
     * @Date 2021/4/1
     * @Param labelid
     */
    @GetMapping("/problem/waitlist/{labelid}/{page}/{size}")
    public Result waitlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.waitList(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功!", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 根据id查询标签
     * @Date 2021/4/11
     * @Param labelId
     */
    @GetMapping("/problem/label/{labelId}")
    public Result findByLableId(@PathVariable("labelId") String labelId) {
        return baseClient.findById(labelId);
    }
}
