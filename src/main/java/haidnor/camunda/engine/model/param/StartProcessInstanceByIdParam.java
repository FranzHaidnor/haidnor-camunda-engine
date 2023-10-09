package haidnor.camunda.engine.model.param;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据流程定义 id 开启流程实例方法参数
 */
public class StartProcessInstanceByIdParam {
    /**
     * 流程定义 id
     */
    private String processDefinitionId;
    /**
     * 业务 key
     */
    private String businessKey;

    private String caseInstanceId;

    /**
     * 变量
     */
    private Map<String, Object> variables;

    public void putVariable(String key, Object value) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        variables.put(key, value);
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

}
