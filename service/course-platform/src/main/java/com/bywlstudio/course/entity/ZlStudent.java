package com.bywlstudio.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ZlStudent对象", description="学生表")
public class ZlStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "学生姓名")
    @NotNull
    private String name;

    @ApiModelProperty(value = "院系")
    @NotNull
    private String department;

    @ApiModelProperty(value = "专业")
    @NotNull
    private String major;

    @ApiModelProperty(value = "可选学分")
    @NotNull
    private Integer credits;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @JsonIgnore
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    private Date gmtModified;


}
