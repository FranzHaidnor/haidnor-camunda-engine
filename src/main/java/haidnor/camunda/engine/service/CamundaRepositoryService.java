package haidnor.camunda.engine.service;

import haidnor.camunda.engine.model.dto.UserTaskDTO;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CamundaRepositoryService {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询流程定义中所有的用户任务节点
     * <p>
     * 该方法用于无并行网关的流程
     *
     * @param processDefinitionId 流程定义 id
     * @return 如果查询不到将会返回一个空集合
     * @author wang xiang
     */
    public List<UserTaskDTO> getProcessDefinitionUserTaskList(String processDefinitionId) {
        List<UserTaskDTO> list = new ArrayList<>();

        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        if (bpmnModelInstance == null) {
            return list;
        }

        Collection<UserTask> userTaskCollection = bpmnModelInstance.getModelElementsByType(UserTask.class);
        for (UserTask userTask : userTaskCollection) {
            UserTaskDTO item = new UserTaskDTO();
            list.add(item);

            item.setId(userTask.getId());
            item.setName(userTask.getName());
            item.setCamundaCandidateUsers(userTask.getCamundaCandidateUsers());
            item.setCamundaCandidateGroups(userTask.getCamundaCandidateGroups());
            item.setCamundaAssignee(userTask.getCamundaAssignee());
            item.setCamundaDueDate(userTask.getCamundaDueDate());
            item.setCamundaFollowUpDate(userTask.getCamundaFollowUpDate());
            item.setCamundaPriority(userTask.getCamundaPriority());
        }
        return list;
    }

}
