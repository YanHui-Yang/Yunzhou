package com.yyh.user.pojo;

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
@Table(name = "tb_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin implements Serializable {

    @Id
    private String id;//ID
    private String loginname;//登陆名称
    private String password;//密码
    private String state;//状态
}
