package com.bwin.activitidemo.controller;

import com.bwin.activitidemo.entity.Role;
import com.bwin.activitidemo.entity.User;
import com.bwin.activitidemo.mapper.RoleMapper;
import com.bwin.activitidemo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserComtroller {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @PostMapping
    public User postUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userMapper.insert(user);
        List<Role> roles = roleMapper.selectList(null);
        for (Role role : roles) {
            userMapper.insertUserRole(user.getId(), role.getId());
        }
        return user;
    }

}
