package com.yyh.recruit.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_recruit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit implements Serializable {

    @Id
    private String id;//ID
    private String jobname;//职位名称
    private String salary;//薪资范围
    private String conditions;//经验要求
    private String education;//学历要求
    private String type;//任职方式
    private String address;//办公地址
    private String eid;//企业ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;//创建日期
    private String state;//状态
    private String url;//网址
    private String label;//标签
    private String content1;//职位描述
    private String content2;//职位要求
}
