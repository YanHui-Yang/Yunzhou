package com.yyh.user.pojo;

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
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    private String id;//ID
    private String loginname;//用户名
    private String mobile;//电话
    private String password;//密码
    private String nickname;//昵称
    private String sex;//性别
    private java.util.Date birthday;//出生年月日
    private String avatar;//头像
    private String email;//E-Mail
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regdate;//注册日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedate;//修改日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastdate;//最后登陆日期
    private Long online;//在线时长（分钟）
    private String interest;//兴趣
    private String personality;//个性
    private Integer fanscount;//粉丝数
    private Integer followcount;//关注数
}
