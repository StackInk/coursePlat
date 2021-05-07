package com.bywlstudio.course.service.impl;

import com.bywlstudio.course.entity.ZlCourseStudent;
import com.bywlstudio.course.mapper.ZlCourseStudentMapper;
import com.bywlstudio.course.service.IZlCourseStudentService;
import com.bywlstudio.course.service.IZlCourseTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生，课程关系表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service
public class ZlCourseStudentImpl extends ServiceImpl<ZlCourseStudentMapper, ZlCourseStudent> implements IZlCourseStudentService {

}
