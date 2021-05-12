package com.bywlstudio.rabbit.mapper;

import com.bywlstudio.rabbit.entity.ZlCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author StackInk
 * @since 2021-05-11
 */
public interface ZlCourseMapper extends BaseMapper<ZlCourse> {


    @Update("update zl_course set stock = #{stock} where id = #{id}")
    void updateStockById(Long id, Integer stock);


}
