package com.bwin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bwin.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{roleId})")
    int insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    @Select("select * from user where username = #{username}")
    @Results({
            @Result(id=true, column="id", property="id"),
            @Result(column="id", property="roles",
                    many=@Many(
                            select="com.bwin.mapper.RoleMapper.selectByUserId",
                            fetchType= FetchType.EAGER
                    )
            )
    })
    User selectByUsername(@Param("username") String username);

}
