package com.bywlstudio.course.mapper;

import com.bywlstudio.course.entity.ZlStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bywlstudio.course.entity.ZlTeacher;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface ZlStudentMapper extends BaseMapper<ZlStudent> {


    /**
     * 根据课程ID获取对应的教师信息
     * @param courseId
     * @return 返回教师列表
     */
    @Select("select t1.* from zl_student t1 join zl_course_student t2 on t2.sid = t1.id where t2.cid = #{courseId}")
    List<ZlStudent> getStudentByCourseId(Long courseId);

}
