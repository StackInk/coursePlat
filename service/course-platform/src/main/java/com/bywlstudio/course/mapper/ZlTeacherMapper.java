package com.bywlstudio.course.mapper;

import com.bywlstudio.course.entity.ZlTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 教师表 Mapper 接口
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface ZlTeacherMapper extends BaseMapper<ZlTeacher> {


    /**
     * 根据课程ID获取对应的教师信息
     * @param courseId
     * @return 返回教师列表
     */
    @Select("select t1.* from zl_teacher t1 join zl_course_teacher t2 on t2.tid = t1.id where t2.cid = #{courseId}")
    List<ZlTeacher> getTeacherByCourseId(Long courseId);

}
