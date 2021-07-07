package com.yyh.article.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yyh.article.pojo.Article;
import com.yyh.article.service.ArticleService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/article")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String name) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Article> pageDate = articleService.pageQuery(name, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * 增加
     *
     * @param article
     */
    @PostMapping("/article")
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @PutMapping("/article/{id}")
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/article/{id}")
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    /**
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 更新文章状态，1审核通过，0未审核
     * @Date 2021/4/1
     * @Param articleId
     */
    @PutMapping("/article/examine/{articleId}")
    public Result examine(@PathVariable String articleId) {
        articleService.updateState(articleId);
        return new Result(StatusCode.OK, "审核成功");
    }

    /**
     * @return
     * @Author Tong
     * @Description 点赞
     * @Date 2021/4/1
     * @Param null
     */
    @PutMapping("/article/thumbup/{articleId}")
    public Result thumbup(@PathVariable String articleId) {
        articleService.addThumbup(articleId);
        return new Result(StatusCode.OK, "点赞成功");
    }

    @DeleteMapping("/article/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        System.out.println(str);
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        System.out.println(ids);
        articleService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }
}
