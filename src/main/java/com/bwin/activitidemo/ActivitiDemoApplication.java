package com.bwin.activitidemo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <a href="https://stackoverflow.com/questions/51162556/how-to-properly-set-activiti-framework-for-spring-boot2/51321770#51321770">How to properly set activiti framework for Spring-boot2</a>
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiDemoApplication.class, args);
    }

}

