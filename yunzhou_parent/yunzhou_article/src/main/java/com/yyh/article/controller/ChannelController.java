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

import com.yyh.article.pojo.Channel;
import com.yyh.article.service.ChannelService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping("/channel")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String name) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Channel> pageDate = channelService.pageQuery(name, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * 增加
     *
     * @param channel
     */
    @PostMapping("channel")
    public Result add(@RequestBody Channel channel) {
        channelService.add(channel);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param channel
     */
    @PutMapping("/channel/{id}")
    public Result update(@RequestBody Channel channel, @PathVariable String id) {
        channel.setId(id);
        channelService.update(channel);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/channel/{id}")
    public Result delete(@PathVariable String id) {
        channelService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    @DeleteMapping("/channel/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        channelService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }

}
