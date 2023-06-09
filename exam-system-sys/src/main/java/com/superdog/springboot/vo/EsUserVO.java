package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "EsUserVO", description = "EsUserVO")
public class EsUserVO extends BaseVO {

    @ApiModelProperty(value = "用户名",example = "test")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码",example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "角色",example = "teacher")
    @NotBlank(message = "角色不能为空")
    private String role;

    @ApiModelProperty(value = "邮箱",example = "test@example.com")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EsUserVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
