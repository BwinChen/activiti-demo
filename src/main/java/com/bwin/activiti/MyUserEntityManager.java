package com.bwin.activiti;

import com.bwin.security.MyUserDetailsService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyUserEntityManager extends UserEntityManagerImpl {

    private MyUserDetailsService userDetailsService;

    public MyUserEntityManager(ProcessEngineConfigurationImpl processEngineConfiguration, UserDataManager userDataManager, MyUserDetailsService userDetailsService) {
        super(processEngineConfiguration, userDataManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserEntity findById(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (userDetails != null) {
            UserEntityImpl user = new UserEntityImpl();
            user.setId(userId);
            return user;
        }
        return null;
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        List<User> users;
        if (query.getGroupId() != null) {
            users = userDetailsService.findUsersInGroup(query.getGroupId())
                    .stream()
                .map(username -> {
                    User user = new UserEntityImpl();
                    user.setId(username);
                    return user;
                })
                .collect(Collectors.toList());
            if (page != null) {
                return users.subList(page.getFirstResult(), page.getFirstResult() + page.getMaxResults());

            }
            return users;
        }
        if (query.getId() != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(query.getId());
            if (userDetails != null) {
                UserEntityImpl user = new UserEntityImpl();
                user.setId(query.getId());
                return Collections.singletonList(user);
            }
        }
        return null;
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        return true;
    }

    public User createNewUser(String userId) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public void updateUser(User updatedUser) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public void delete(UserEntity userEntity) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    @Override
    public void deletePicture(User user) {
        UserEntity userEntity = (UserEntity) user;
        if (userEntity.getPictureByteArrayRef() != null) {
            userEntity.getPictureByteArrayRef()
                .delete();
        }
    }

    public void delete(String userId) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return findUserByQueryCriteria(query, null).size();
    }

    public List<Group> findGroupsByUser(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (userDetails != null) {
            return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> {
                    Group g = new GroupEntityImpl();
                    g.setId(a);
                    return g;
                })
                .collect(Collectors.toList());
        }
        return null;
    }

    public UserQuery createNewUserQuery() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
