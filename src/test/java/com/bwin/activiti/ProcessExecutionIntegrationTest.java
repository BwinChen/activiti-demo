package com.bwin.activiti;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessExecutionIntegrationTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Test
    public void givenBPMN_whenDeployProcess_thenDeployed() {
//        repositoryService.createDeployment()
//          .addClasspathResource("processes/vacationRequest.bpmn20.xml")
//          .deploy();
        long count = repositoryService.createProcessDefinitionQuery().count();
        assertTrue(count >= 1);
    }

    @Test
    public void givenProcessDefinition_whenStartProcessInstance_thenProcessRunning() {
//        repositoryService.createDeployment()
//          .addClasspathResource("processes/vacationRequest.bpmn20.xml")
//          .deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", 4);
        variables.put("vacationMotivation", "I'm really tired!");

        ProcessInstance processInstance = runtimeService
          .startProcessInstanceByKey("vacationRequest", variables);

        long count = runtimeService.createProcessInstanceQuery().count();
        assertTrue(count >= 1);
    }

    @Test
    public void givenProcessInstance_whenCompleteTask_thenProcessExecutionContinues() {
//        repositoryService.createDeployment()
//          .addClasspathResource("processes/vacationRequest.bpmn20.xml")
//          .deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", 4);
        variables.put("vacationMotivation", "I'm really tired!");

        ProcessInstance processInstance = runtimeService
          .startProcessInstanceByKey("vacationRequest", variables);

        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();

        Task task = tasks.get(0);

        Map<String, Object> taskVariables = new HashMap<>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("comments", "We have a tight deadline!");
        taskService.complete(task.getId(), taskVariables);

        Task currentTask = taskService.createTaskQuery().taskName("Modify vacation request").singleResult();
        assertNotNull(currentTask);
    }

    @Test(expected = ActivitiException.class)
    public void givenProcessDefinition_whenSuspend_thenNoProcessInstanceCreated() {
//        repositoryService.createDeployment()
//          .addClasspathResource("processes/vacationRequest.bpmn20.xml")
//          .deploy();

        repositoryService.suspendProcessDefinitionByKey("vacationRequest");
        runtimeService.startProcessInstanceByKey("vacationRequest");
//        repositoryService.activateProcessDefinitionByKey("vacationRequest");
    }

}
