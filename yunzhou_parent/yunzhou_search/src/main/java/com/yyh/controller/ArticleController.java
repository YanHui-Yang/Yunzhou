package com.yyh.controller;

import com.yyh.entity.PageResult;
import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.pojo.Article;
import com.yyh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Tong
 * Date: 2021/4/4
 * Description: 文章的Controller层
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "新增成功!");
    }

    @RequestMapping(value = "/{page}/{size}/{keyword}", method = RequestMethod.GET)
    public Result searchByKeyWords(@PathVariable String keyword,
                                   @PathVariable int page,
                                   @PathVariable int size) {

        Page<Article> pageDate = articleService.searchPageData(keyword, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageDate.getTotalElements(), pageDate.getContent()));
    }
}
