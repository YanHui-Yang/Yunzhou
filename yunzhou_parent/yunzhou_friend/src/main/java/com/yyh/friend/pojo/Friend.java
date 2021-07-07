package com.yyh.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description: 交友模块的实体类
 */
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friend implements Serializable {

    @Id
    private String userid;
    @Id
    private String friendid;
    private String islike;
}
