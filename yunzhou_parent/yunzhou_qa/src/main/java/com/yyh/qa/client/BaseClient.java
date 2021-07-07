package com.yyh.qa.client;

import com.yyh.entity.Result;
import com.yyh.qa.client.impl.BaseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description: 模块调用的接口
 */
@FeignClient(value = "yunzhou-base", fallback = BaseClientImpl.class)
public interface BaseClient {

    /**
     * @return com.yyh.entity.Result
     * @Author Tong
     * @Description 根据id查询标签
     * @Date 2021/4/11
     * @Param labelId
     */
    @GetMapping("/label/{labelId}")
    Result findById(@PathVariable("labelId") String labelId);
}
