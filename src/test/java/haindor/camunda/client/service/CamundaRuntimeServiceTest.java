package haindor.camunda.client.service;

import haidnor.camunda.engine.HaidnorCamundaEngineApplication;
import haidnor.camunda.engine.model.dto.ProcessInstanceDTO;
import haidnor.camunda.engine.model.dto.TaskDTO;
import haidnor.camunda.engine.model.param.StartProcessInstanceByIdParam;
import haidnor.camunda.engine.service.CamundaRuntimeService;
import haidnor.camunda.engine.service.CamundaTaskService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {HaidnorCamundaEngineApplication.class})
public class CamundaRuntimeServiceTest {

    @Autowired
    private CamundaRuntimeService runtimeService;

    @Autowired
    private CamundaTaskService camundaTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Test
    public void test_getActiveTask() throws Exception {
        TaskDTO activeTask = camundaTaskService.getActiveTask("764e8941-6735-11ee-93b2-00155d6330fb");
    }

    @Test
    public void test_startProcessInstanceById() throws Exception {
        StartProcessInstanceByIdParam param = new StartProcessInstanceByIdParam();
        param.setProcessDefinitionId("wangxiang_test:1:c7819116-538f-11ee-98fb-78af0843873a");
        param.putVariable("user1", "1");
        param.putVariable("user2", "2");
        param.putVariable("user3", "3");

        ProcessInstanceDTO processInstanceDTO = runtimeService.startProcessInstanceById(param);
    }

    @Test
    public void test_() throws Exception {
        List<String> list1 = new ArrayList<>();
        list1.add("R_1");
        list1.add("R_2");
        list1.add("U_1");

        // 如果用户任务同时设置了 Assignee,和 candidate groups, 则无法根据 candidate groups (候选组)查询
        // 在有指派用户的情况下, 所有的候选选项都无法生效
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateGroupIn(list1)
                .listPage(1,10);

        for (Task task : list) {
            task.getAssignee();
        }
    }


}
