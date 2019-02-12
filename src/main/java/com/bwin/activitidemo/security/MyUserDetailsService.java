package com.bwin.activitidemo.security;

import com.bwin.activitidemo.entity.Role;
import com.bwin.activitidemo.entity.User;
import com.bwin.activitidemo.mapper.RoleMapper;
import com.bwin.activitidemo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = createAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
    }

    public List<String> findUsersInGroup(String groupName) {
        List<String> usernames = new ArrayList<>();
        List<User> users = roleMapper.selectUsersByRoleName(groupName);
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    /**
     * @param roles 角色集合
     * @return 权限集合
     */
    private List<SimpleGrantedAuthority> createAuthorities(List<Role> roles){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (Role role : roles){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getEnName()));
        }
        return simpleGrantedAuthorities;
    }

}