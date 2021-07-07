package com.yyh.article.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_column")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Column implements Serializable {

    @Id
    private String id;//ID
    private String name;//专栏名称
    private String summary;//专栏简介
    private String userid;//用户ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createtime;//申请日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date checktime;//审核日期
    private String state;//状态
}
