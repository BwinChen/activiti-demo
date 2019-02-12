package com.bwin.activitidemo.controller;

import com.bwin.activitidemo.entity.Role;
import com.bwin.activitidemo.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/role")
@RestController
public class RoleController {

    private final RoleMapper roleMapper;

    @PostMapping
    public Role postRole(Role role) {
        roleMapper.insert(role);
        return role;
    }

}
