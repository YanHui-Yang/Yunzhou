package com.yyh.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Tong
 * Date: 2021/3/30
 * Description: 返回结果
 */
@Data
@ToString
@NoArgsConstructor
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;
    private Long totalCount;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data, Long totalCount) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.totalCount = totalCount;
    }
}
