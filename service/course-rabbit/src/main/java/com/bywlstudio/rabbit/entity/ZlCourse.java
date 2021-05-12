package com.bywlstudio.rabbit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author StackInk
 * @since 2021-05-11
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
    private String name;

    @ApiModelProperty(value = "课程类型(0->智慧树,1->慕课,2->线下)")
    private Boolean type;

    @ApiModelProperty(value = "课程上课地址")
    private String place;

    @ApiModelProperty(value = "课程库存")
    private Integer stock;

    @ApiModelProperty(value = "课程开始时间")
    private Date startTime;

    @ApiModelProperty(value = "课程上课时间")
    private String time;

    @ApiModelProperty(value = "是否参与选课(0->不参与,1->参与选课)")
    private Boolean isSelect;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
