package com.bywlstudio.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.mapper.ZlStudentMapper;
import com.bywlstudio.course.service.IZlStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.course.vo.CourseVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service
public class ZlStudentServiceImpl extends ServiceImpl<ZlStudentMapper, ZlStudent> implements IZlStudentService {

    @Override
    public IPage<ZlStudent> getPageListByName(Long page, Long limit, String name) {
        Page<ZlStudent> page1 = new Page<>(page,limit);
        IPage<ZlStudent> page2 = this.page(page1, new QueryWrapper<ZlStudent>().like("name",name));
        return page2;
    }

    @Override
    public List<ZlStudent> getStudentByCourseId(Long id) {
        List<ZlStudent> studentList = baseMapper.getStudentByCourseId(id);
        return studentList;
    }
}
