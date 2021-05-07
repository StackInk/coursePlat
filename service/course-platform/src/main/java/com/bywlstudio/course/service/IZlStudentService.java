package com.bywlstudio.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bywlstudio.course.entity.ZlStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.vo.CourseVo;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface IZlStudentService extends IService<ZlStudent> {

    /**
     * 根据课程ID获取学生信息
     * @param id
     * @return
     */
    List<ZlStudent> getStudentByCourseId(Long id);

    IPage<ZlStudent> getPageListByName(Long page, Long limit, String name);
}
