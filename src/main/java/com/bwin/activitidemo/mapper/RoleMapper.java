package com.bwin.activitidemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bwin.activitidemo.entity.Role;
import com.bwin.activitidemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from user where id in (select user_id from user_role ur, role r where ur.role_id = r.id and r.en_name = #{roleName})")
    List<User> selectUsersByRoleName(@Param("roleName") String roleName);

    @Select("select * from role where id in (select role_id from user_role where user_id = #{userId})")
    List<Role> selectByUserId(@Param("userId") String userId);

}