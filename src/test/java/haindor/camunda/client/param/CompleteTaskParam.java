package haindor.camunda.client.param;


import java.util.HashMap;
import java.util.Map;

public class CompleteTaskParam {

    private String taskId;

    private Map<String, Object> variables;

    public void putVariable(String key, Object value) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        variables.put(key, value);
    }

}
