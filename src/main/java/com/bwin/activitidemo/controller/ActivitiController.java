package com.bwin.activitidemo.controller;

import com.bwin.activitidemo.entity.TaskRepresentation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
public class ActivitiController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @GetMapping("/start-process")
    public String startProcess() {
        runtimeService.startProcessInstanceByKey("my-process");
        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
          .count();
    }

    @GetMapping("/get-tasks/{processInstanceId}")
    public List<TaskRepresentation> getTasks(@PathVariable String processInstanceId) {
        List<Task> usertasks = taskService.createTaskQuery()
          .processInstanceId(processInstanceId)
          .list();

        return usertasks.stream()
          .map(task -> new TaskRepresentation(task.getId(), task.getName(), task.getProcessInstanceId()))
          .collect(Collectors.toList());
    }

    @GetMapping("/complete-task-A/{processInstanceId}")
    public String completeTaskA(@PathVariable String processInstanceId) {
        Task task = taskService.createTaskQuery()
          .processInstanceId(processInstanceId)
          .singleResult();
        taskService.complete(task.getId());
        return "Task completed" + task;
    }

}
