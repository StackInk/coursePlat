package com.bywlstudio.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bywlstudio.course.entity.ZlTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教师表 服务类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface IZlTeacherService extends IService<ZlTeacher> {

    List<ZlTeacher> getTeachersByCourseId(Long courseId);

    IPage<ZlTeacher> getPageListByName(Long page, Long limit, String name);
}
