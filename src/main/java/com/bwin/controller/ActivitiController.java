package com.bwin.controller;

import com.bwin.entity.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="https://www.baeldung.com/spring-activiti">Introduction to Activiti with Spring</a>
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/activiti")
@RestController
public class ActivitiController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final IdentityService identityService;

    @GetMapping("/start-process")
    public String startProcess() {
        runtimeService.startProcessInstanceByKey("my-process");
        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
          .count();
    }

    @GetMapping("/get-tasks/{processInstanceId}")
    public List<Task> getTasks(@PathVariable String processInstanceId) {
        List<org.activiti.engine.task.Task> usertasks = taskService.createTaskQuery()
          .processInstanceId(processInstanceId)
          .list();

        return usertasks.stream()
          .map(task -> new Task(task.getId(), task.getName(), task.getProcessInstanceId()))
          .collect(Collectors.toList());
    }

    @GetMapping("/complete-task-A/{processInstanceId}")
    public String completeTaskA(@PathVariable String processInstanceId) {
        org.activiti.engine.task.Task task = taskService.createTaskQuery()
          .processInstanceId(processInstanceId)
          .singleResult();
        taskService.complete(task.getId());
        return "Task completed" + task;
    }

    @GetMapping("/protected-process")
    public String protectedrocess() {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        identityService.setAuthenticatedUserId(userId);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("protected-process");

        List<org.activiti.engine.task.Task> usertasks = taskService.createTaskQuery()
                .processInstanceId(pi.getId())
                .list();

        taskService.complete(usertasks.iterator()
                .next()
                .getId());

        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
                .count();
    }

}
