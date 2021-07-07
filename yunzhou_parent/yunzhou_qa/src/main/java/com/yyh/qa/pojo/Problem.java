package com.yyh.qa.pojo;

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
@Table(name = "tb_problem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Problem implements Serializable {

    @Id
    private String id;//ID
    private String title;//标题
    private String content;//内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createtime;//创建日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updatetime;//修改日期
    private String userid;//用户ID
    private String nickname;//昵称
    private Long visits;//浏览量
    private Long thumbup;//点赞数
    private Long reply;//回复数
    private String solve;//是否解决
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String replyname;//回复人昵称
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date replytime;//回复日期
}
