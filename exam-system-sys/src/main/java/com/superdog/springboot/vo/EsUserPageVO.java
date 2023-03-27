package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EsUserPageVO", description = "EsUserPageVO")
public class EsUserPageVO extends PageVO {

    @ApiModelProperty(value = "用户名",example = "")
    private String username;

    @ApiModelProperty(value = "密码",example = "")
    private String password;

    @ApiModelProperty(value = "角色",example = "")
    private String role;

    @ApiModelProperty(value = "邮箱",example = "")
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
        return "EsUserPageVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
