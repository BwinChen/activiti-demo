package com.bwin.activiti;

import com.bwin.security.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisGroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisUserDataManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @see <a href="https://www.baeldung.com/activiti-spring-security">Activiti with Spring Security</a>
 */
@AllArgsConstructor
@Configuration
public class ActivitiConfig {

    private final SpringProcessEngineConfiguration processEngineConfiguration;
    private final MyUserDetailsService userDetailsService;

    @Bean
    InitializingBean processEngineInitializer() {
        return () -> {
            processEngineConfiguration.setUserEntityManager(new MyUserEntityManager(processEngineConfiguration, new MybatisUserDataManager(processEngineConfiguration), userDetailsService));
            processEngineConfiguration.setGroupEntityManager(new MyGroupEntityManager(processEngineConfiguration, new MybatisGroupDataManager(processEngineConfiguration), userDetailsService));
        };
    }

}
