package com.yyh.base.controller;

import com.yyh.base.pojo.Label;
import com.yyh.base.service.LabelService;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tong
 * Date: 2021/3/31
 * Description: 基础模块.Label的controller层
 */
@RestController
@RefreshScope
public class LabelController {
    @Autowired
    private LabelService labelService;

    @RequestMapping("/label/options")
    public List<Label> findAll() {
        return labelService.findAll();
    }

    @GetMapping("/label")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String labelname) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Label> pageDate = labelService.pageQuery(labelname, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 保存标签
     * @Date 2021/4/1
     * @Param label
     */
    @PostMapping("/label")
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(StatusCode.OK, "保存成功!");
    }

    /**
     * @param label
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 根据标签的id, 修改标签
     * @Date 2021/4/1
     * @Param labelId
     */
    @PutMapping("/label/{labelId}")
    public Result update(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(StatusCode.OK, "修改成功!");
    }

    /**
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 根据标签的id, 删除标签
     * @Date 2021/4/1
     * @Param labelId
     */
    @DeleteMapping("/label/{labelId}")
    public Result deleteById(@PathVariable("labelId") String labelId) {
        labelService.deleteById(labelId);
        return new Result(StatusCode.OK, "删除成功!");
    }

    @DeleteMapping("/label/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        labelService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }

}
