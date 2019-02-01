package com.bwin.activitidemo.security;

import lombok.AllArgsConstructor;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisGroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisUserDataManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@AllArgsConstructor
@Configuration
public class ActivitiSecurityConfig {

    private final SpringProcessEngineConfiguration processEngineConfiguration;
    private final JdbcUserDetailsManager userManager;

    @Bean
    InitializingBean processEngineInitializer() {
        return () -> {
            processEngineConfiguration.setUserEntityManager(new SpringSecurityUserManager(processEngineConfiguration, new MybatisUserDataManager(processEngineConfiguration), userManager));
            processEngineConfiguration.setGroupEntityManager(new SpringSecurityGroupManager(processEngineConfiguration, new MybatisGroupDataManager(processEngineConfiguration)));
        };
    }

}
