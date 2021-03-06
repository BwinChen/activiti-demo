package com.bwin.activiti;

import com.bwin.security.MyUserDetailsService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyGroupEntityManager extends GroupEntityManagerImpl {

    private MyUserDetailsService userDetailsService;

    public MyGroupEntityManager(ProcessEngineConfigurationImpl processEngineConfiguration, GroupDataManager groupDataManager, MyUserDetailsService userDetailsService) {
        super(processEngineConfiguration, groupDataManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        if (query.getUserId() != null) {
            return findGroupsByUser(query.getUserId());
        }
        return null;
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return findGroupByQueryCriteria(query, null).size();
    }

    @Override
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

    public Group createNewGroup(String groupId) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    @Override
    public void delete(String groupId) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public GroupQuery createNewGroupQuery() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
