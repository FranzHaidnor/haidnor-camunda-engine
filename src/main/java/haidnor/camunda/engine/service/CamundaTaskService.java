package haidnor.camunda.engine.service;

import haidnor.camunda.engine.model.dto.TaskDTO;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CamundaTaskService {

    @Autowired
    private TaskService taskService;

    /**
     * 根据流程查询当前节点
     * <p>
     * 该方法仅限用于无并行网关的流程, 在同一时刻只能有一个活跃的流程任务
     *
     * @param processInstanceId 流程实例 id
     */
    public TaskDTO getActiveTask(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        if (Objects.isNull(task)) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProcessDefinitionId(task.getProcessDefinitionId());
        taskDTO.setTaskDefinitionKey(task.getTaskDefinitionKey());
        taskDTO.setId(task.getId());
        return taskDTO;
    }

}
