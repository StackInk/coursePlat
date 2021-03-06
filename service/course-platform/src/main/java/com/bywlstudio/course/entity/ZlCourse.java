package com.bywlstudio.course.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

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
 * 课程表
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ZlCourse对象", description="课程表")
public class ZlCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课程名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "课程类型(0->智慧树,1->慕课,2->线下)")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "课程上课地址")
    private String place;

    @ApiModelProperty(value = "课程库存")
    @NotNull
    private Integer stock;

    @ApiModelProperty(value = "开始上课时间")
    @NotNull
    private Date startTime;

    @ApiModelProperty(value = "上课时间")
    private String time;

    @ApiModelProperty(value = "当前课程是否参加选课(0->不参加,1->参加)")
    private Integer isSelect;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @JsonIgnore
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT )
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date gmtModified;

    public ZlCourse(){}

    public ZlCourse(@NotNull String name, @NotNull Integer type, String place, @NotNull Integer stock, @NotNull Date startTime, String time) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.stock = stock;
        this.startTime = startTime;
        this.time = time;
    }
}
