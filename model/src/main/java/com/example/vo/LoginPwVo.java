package com.example.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPwVo {
    private String phone;
    @ApiModelProperty(value = "密码")
    private String password;
}
