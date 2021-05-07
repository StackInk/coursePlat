package com.bywlstudio.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.mapper.ZlTeacherMapper;
import com.bywlstudio.course.service.IZlTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service
public class ZlTeacherServiceImpl extends ServiceImpl<ZlTeacherMapper, ZlTeacher> implements IZlTeacherService {

    @Override
    public IPage<ZlTeacher> getPageListByName(Long page, Long limit, String name) {
        Page<ZlTeacher> page1 = new Page<>(page,limit);
        IPage<ZlTeacher> page2 = this.page(page1, new QueryWrapper<ZlTeacher>().like("name",name));
        return page2;
    }

    @Override
    public List<ZlTeacher> getTeachersByCourseId(Long courseId) {
        List<ZlTeacher> teacherList = baseMapper.getTeacherByCourseId(courseId);
        return teacherList;
    }
}
