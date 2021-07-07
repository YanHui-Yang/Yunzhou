package com.yyh.qa.client.impl;

import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.qa.client.BaseClient;
import org.springframework.stereotype.Component;

/**
 * Author: Tong
 * Date: 2021/4/13
 * Description: 服务雪崩熔断器跳转方向
 */
@Component
public class BaseClientImpl implements BaseClient {

    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR, "服务雪崩，熔断器触发");
    }
}
