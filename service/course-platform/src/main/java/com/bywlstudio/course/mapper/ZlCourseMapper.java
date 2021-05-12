package com.bywlstudio.course.mapper;

import com.bywlstudio.course.entity.ZlCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface ZlCourseMapper extends BaseMapper<ZlCourse> {


    @Update("<script>" +
            "UPDATE zl_course set is_select = #{code} where id in\n" +
            "   <foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "   </foreach>" +
            "</script>")
    void updateCourseByIds(Long[] ids, int code);


}
