package com.bwin.controller;

import com.bwin.entity.Role;
import com.bwin.entity.User;
import com.bwin.mapper.RoleMapper;
import com.bwin.mapper.UserMapper;
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
