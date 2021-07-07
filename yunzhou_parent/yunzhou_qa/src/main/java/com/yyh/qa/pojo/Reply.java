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
@Table(name = "tb_reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reply implements Serializable {

    @Id
    private String id;//编号
    private String problemid;//问题ID
    private String content;//回答内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createtime;//创建日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updatetime;//更新日期
    private String userid;//回答人ID
    private String nickname;//回答人昵称
}
