package haidnor.camunda.engine.app;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

//@Component
public class Application implements ApplicationRunner {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        ProcessInstance processUserTask = runtimeService.startProcessInstanceByKey("Process_001");

        Incident incident = runtimeService.createIncidentQuery()
                .processDefinitionKeyIn("Process_001")
                .processDefinitionId("Process_001:1:967c988a-4e42-11ee-b62e-78af0843873a")
                .processInstanceId("96782bb8-4e42-11ee-b62e-78af0843873a")
                .singleResult();


        List<Task> list = taskService.createTaskQuery()
                .caseDefinitionKey("Process_001")
                .caseInstanceId("Process_001:1:967c988a-4e42-11ee-b62e-78af0843873a")
                .list();
        System.out.println(list);
    }

    /**
     * 创建流程实例
     */
    private void startProcessInstanceByKey() {
    }

}
