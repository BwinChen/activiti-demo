package com.bwin.activitidemo.controller;

import lombok.AllArgsConstructor;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
public class ProcessController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final IdentityService identityService;

    @GetMapping("/protected-process")
    public String startProcess() {

        String userId = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        identityService.setAuthenticatedUserId(userId);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("protected-process");

        List<Task> usertasks = taskService.createTaskQuery()
            .processInstanceId(pi.getId())
            .list();

        taskService.complete(usertasks.iterator()
            .next()
            .getId());

        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
            .count();
    }

}
