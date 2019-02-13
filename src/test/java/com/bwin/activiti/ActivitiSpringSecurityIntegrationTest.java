package com.bwin.activiti;

import org.activiti.engine.IdentityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiSpringSecurityIntegrationTest {

    @Autowired
    private IdentityService identityService;

    @Test
    public void whenUserExists_thenOk() {
        identityService.setUserPicture("tom", null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenUserNonExistent_thenSpringException() {
        identityService.setUserPicture("jerry", null);
    }

}
