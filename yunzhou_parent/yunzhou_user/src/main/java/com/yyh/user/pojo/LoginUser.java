package com.yyh.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author: Tong
 * Date: 2021/4/21
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUser {
    private String username;
    private String password;
}
