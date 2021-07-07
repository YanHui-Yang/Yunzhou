package com.yyh.base.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author: Tong
 * Date: 2021/3/30
 * Description: 标签的实体类
 */
@Entity
@Table(name = "tb_label")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Label implements Serializable {
    @Id
    private String id;  //标签的id
    private String labelname;   //标签的名称
    private String state;   //状态
    private Long count; //使用的数量
    private Long fans;  //关注数
    private String recommend;   //是否推荐
}
