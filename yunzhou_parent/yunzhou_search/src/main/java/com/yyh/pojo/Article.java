package com.yyh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * Author: Tong
 * Date: 2021/4/4
 * Description: 文章存储到索引库的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "yunzhou_article", type = "article")
public class Article implements Serializable {

    @Id
    private String id;  //文章id
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;   //文章标题
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content; //文章内容
    private String state;   //审核内容
}
