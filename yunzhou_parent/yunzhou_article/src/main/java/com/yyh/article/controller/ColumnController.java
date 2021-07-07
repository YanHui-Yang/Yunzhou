package com.yyh.article.controller;

import com.yyh.article.pojo.Column;
import com.yyh.article.service.ColumnService;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
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
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    @GetMapping("/column")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String name) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Column> pageDate = columnService.pageQuery(name, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * 增加
     *
     * @param column
     */
    @PostMapping("/column")
    public Result add(@RequestBody Column column) {
        columnService.add(column);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param column
     */
    @PutMapping("/column/{id}")
    public Result update(@RequestBody Column column, @PathVariable String id) {
        column.setId(id);
        columnService.update(column);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/column/{id}")
    public Result delete(@PathVariable String id) {
        columnService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    @DeleteMapping("/column/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        columnService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }

}
