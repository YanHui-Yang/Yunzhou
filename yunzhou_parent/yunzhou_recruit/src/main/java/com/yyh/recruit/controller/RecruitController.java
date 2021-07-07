package com.yyh.recruit.controller;

import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.recruit.pojo.Recruit;
import com.yyh.recruit.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @GetMapping("/recruit")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String jobname) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Recruit> pageDate = recruitService.pageQuery(jobname, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * 增加
     *
     * @param recruit
     */
    @PostMapping("/recruit")
    public Result add(@RequestBody Recruit recruit) {
        recruitService.add(recruit);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @PutMapping("/recruit/{id}")
    public Result update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/recruit/{id}")
    public Result delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    /**
     * @Author Tong
     * @Description 查询推荐职位->2
     * @Date 2021/4/1
     * @Param
     * @return com.yyh.entity.Result
     */
    @GetMapping("/recruit/search/recommend")
    public Result recommend() {
        return new Result(StatusCode.OK, "查询成功！", recruitService.recommend(), 6L);
    }

    /**
     * @Author Tong
     * @Description 查询最新职位
     * @Date 2021/4/1
     * @Param 
     * @return com.yyh.entity.Result
     */
    @GetMapping("/recruit/search/newlist")
    public Result newlist() {
        return new Result(StatusCode.OK, "查询成功！", recruitService.newlist(), 6L);
    }

    @DeleteMapping("/recruit/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        recruitService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }
}
