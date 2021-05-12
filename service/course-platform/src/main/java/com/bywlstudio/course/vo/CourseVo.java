package com.bywlstudio.course.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlTeacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: zl
 * @Date: Create in 2021/4/28 11:59
 * @Description:
 */
@Data
@ApiModel(value = "课程VO，集成了教师和课程")
public class CourseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "课程类型(0->智慧树,1->慕课,2->线下)")
    private Integer type;

    @ApiModelProperty(value = "课程上课地址")
    private String place;

    @ApiModelProperty(value = "课程库存")
    private Integer stock;

    @ApiModelProperty(value = "开始上课时间")
    private Date startTime;

    @ApiModelProperty(value = "上课时间")
    private String time;

    @ApiModelProperty(value = "上课教师")
    private List<ZlTeacher> teachers;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

}
