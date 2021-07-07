package com.yyh.gathering.pojo;

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
@Table(name = "tb_gathering")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gathering implements Serializable {

    @Id
    private String id;//编号
    private String name;//活动名称
    private String summary;//大会简介
    private String detail;//详细说明
    private String sponsor;//主办方
    private String image;//活动图片
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;//截止时间
    private String address;//举办地点
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrolltime;//报名截止
    private String state;//是否可见
}
