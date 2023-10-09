package haindor.camunda.client;

import haidnor.camunda.engine.HaidnorCamundaEngineApplication;
import haindor.camunda.client.param.StartProcessInstanceParam;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest(classes = {HaidnorCamundaEngineApplication.class})
class HaidnorCamundaClientApplicationTests {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    // 查询所有的用户任务节点
    @Test
    public void test_12() throws Exception {
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance("wangxiang_test3:1:9ba9a2c1-664b-11ee-9737-78af0843873a");
        Collection<UserTask> userTaskCollection = bpmnModelInstance.getModelElementsByType(UserTask.class);
        for (UserTask userTask : userTaskCollection) {
            String id = userTask.getId();
            System.out.println(id);
            String camundaCandidateUsers = userTask.getCamundaCandidateUsers();
            System.out.println(camundaCandidateUsers);
        }
    }

    @Test
    public void startProcessInstanceById() throws Exception {
        StartProcessInstanceParam param = new StartProcessInstanceParam();
        param.setProcessDefinitionId("Process_002:1:de9e26fc-51de-11ee-9502-78af0843873a");
        param.putVariable("user1", "1");
        param.putVariable("user2", "2");
        param.putVariable("user3", "3");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(param.getProcessDefinitionId(), param.getBusinessKey(), param.getCaseInstanceId(), param.getVariables());
        System.out.println(processInstance);
    }

}
