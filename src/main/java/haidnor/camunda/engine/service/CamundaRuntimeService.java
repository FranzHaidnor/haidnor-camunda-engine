package haidnor.camunda.engine.service;

import haidnor.camunda.engine.model.dto.ProcessInstanceDTO;
import haidnor.camunda.engine.model.param.StartProcessInstanceByIdParam;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamundaRuntimeService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    /**
     * 流程实例回退到前一个用户任务节点
     * <p>
     * 1.改方法仅限用于无并行网关的流程, 在同一时刻只能有一个活跃的流程任务
     * 2.若当前活跃节点为第一个节点, 将不会有任何操作
     *
     * @author wang xiang
     */
    private void backProcessToPreviousNode(String processInstanceId) {
        // 查询活跃的流程实例
        ActivityInstance activityInstance = runtimeService.getActivityInstance(processInstanceId);
        // 如果流程实例结束将会查询不到, 返回
        if (activityInstance == null) {
            return;
        }

        // 当前流程实例活跃的流程节点. 这里只取第一个 (该方法只用于单线流程, 同一时刻只有一个活跃的流程节点)
        ActivityInstance[] childActivityInstances = activityInstance.getChildActivityInstances();
        if (childActivityInstances == null || childActivityInstances.length == 0) {
            return;
        }
        ActivityInstance activityTask = childActivityInstances[0];

        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType("userTask")
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        // 如果流程没有历史记录,则直接返回
        if (historicActivityInstanceList == null || historicActivityInstanceList.isEmpty()) {
            return;
        }
        HistoricActivityInstance lastTask = null;
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            // 如果当前活跃流程节点是流程的第一个,则直接返回
            if (lastTask == null && historicActivityInstance.getActivityId().equals(activityTask.getActivityId())) {
                return;
            }
            if (lastTask == null) {
                lastTask = historicActivityInstance;
                continue;
            }
            if (historicActivityInstance.getActivityId().equals(activityTask.getActivityId())) {
                break;
            }
            lastTask = historicActivityInstance;
        }
        runtimeService.createProcessInstanceModification(processInstanceId)
                // 取消当前活跃的流程节点
                .cancelActivityInstance(activityTask.getId())
                // 开启点一个流程节点任务
                .startBeforeActivity(lastTask.getActivityId())
                .execute();
    }

    /**
     * 流程实例回退到第一个用户任务节点
     * <p>
     * 1.改方法仅限用于无并行网关的流程, 在同一时刻只能有一个活跃的流程任务
     * 2.若当前活跃节点为第一个节点, 将不会有任何操作
     *
     * @author wang xiang
     */
    private void backProcessToFirstNode(String processInstanceId) {
        // 查询活跃的流程实例
        ActivityInstance activityInstance = runtimeService.getActivityInstance(processInstanceId);
        // 如果流程实例结束将会查询不到, 返回
        if (activityInstance == null) {
            return;
        }

        // 当前流程实例活跃的流程节点. 这里只取第一个 (该方法只用于单线流程, 同一时刻只有一个活跃的流程节点)
        ActivityInstance[] childActivityInstances = activityInstance.getChildActivityInstances();
        if (childActivityInstances == null || childActivityInstances.length == 0) {
            return;
        }
        ActivityInstance activityTask = childActivityInstances[0];

        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType("userTask")
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        // 如果流程没有历史记录,则直接返回
        if (historicActivityInstanceList == null || historicActivityInstanceList.isEmpty()) {
            return;
        }
        HistoricActivityInstance firstTask = historicActivityInstanceList.get(0);
        if (firstTask.getActivityId().equals(activityTask.getActivityId())) {
            return;
        }
        runtimeService.createProcessInstanceModification(processInstanceId)
                // 取消当前活跃的流程节点
                .cancelActivityInstance(activityTask.getId())
                // 开启点一个流程节点任务
                .startBeforeActivity(firstTask.getActivityId())
                .execute();
    }

    /**
     * 判断流程实例是否结束
     *
     * @param processInstanceId 流程实例 id
     */
    public boolean processInstanceIsEnd(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        return processInstance.isEnded();
    }

    /**
     * 根据流程定义 id 开启流程实例方法参数
     */
    public ProcessInstanceDTO startProcessInstanceById(StartProcessInstanceByIdParam param) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(param.getProcessDefinitionId(), param.getBusinessKey(), param.getCaseInstanceId(), param.getVariables());

        ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
        processInstanceDTO.setProcessInstanceId(processInstance.getProcessInstanceId());
        processInstanceDTO.setRootProcessInstanceId(processInstance.getRootProcessInstanceId());
        processInstanceDTO.setBusinessKey(processInstance.getBusinessKey());
        processInstanceDTO.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        processInstanceDTO.setCaseInstanceId(processInstance.getCaseInstanceId());
        return processInstanceDTO;
    }

}
