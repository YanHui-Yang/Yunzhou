package com.yyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Author: Tong
 * Date: 2021/3/30
 * Description: 返回分页对象的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResult<T> {
    private Long total;
    private List<T> rows;
}
