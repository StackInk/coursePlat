package com.bywlstudio.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zl
 * @Date: Create in 2021/4/24 16:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo<E> {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "是否为教师")
    private Integer isTeacher;



}
