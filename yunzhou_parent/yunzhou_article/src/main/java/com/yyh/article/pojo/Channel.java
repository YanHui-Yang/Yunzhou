package com.yyh.article.pojo;

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
@Table(name = "tb_channel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Channel implements Serializable {

    @Id
    private String id;//ID
    private String name;//频道名称
    private String state;//状态
}
