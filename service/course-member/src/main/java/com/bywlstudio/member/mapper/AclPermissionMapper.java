package com.bywlstudio.member.mapper;

import com.bywlstudio.member.entity.AclPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface AclPermissionMapper extends BaseMapper<AclPermission> {


    @Results(
            id = "permissionMap",
            value = {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(property = "pid",column = "pid"),
                    @Result(property = "name",column = "name"),
                    @Result(property = "type",column = "type"),
                    @Result(property = "permissionValue",column = "permissionValue"),
                    @Result(property = "path",column = "path"),
                    @Result(property = "component",column = "component"),
                    @Result(property = "icon",column = "icon"),
                    @Result(property = "status",column = "status")
            }
    )
    @Select("select * from acl_permission t1 inner join acl_role_permission t2 on t2.role_id = #{roleId} and t1.id = t2.permission_id where t1.is_deleted = 0 and t2.is_deleted = 0")
    List<AclPermission> getPermissionByRoleId(Long roleId);

    @Select("select t1.permission_value from acl_permission t1 inner join acl_role_permission t2 on t2.role_id = #{roleId} and t1.id = t2.permission_id where t1.is_deleted = 0 and t2.is_deleted = 0")
    List<String> getPermissionValueByRoleId(Long roleId);




}
