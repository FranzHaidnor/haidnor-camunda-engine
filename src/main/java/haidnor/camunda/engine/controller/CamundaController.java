package haidnor.camunda.engine.controller;

import haidnor.web.http.Result;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/camunda")
public class CamundaController {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 启动流程实例
     */
    @PostMapping("/startProcess")
    public Result startProcess(String processKey) {
        // 设置启动节点的用户名
        identityService.setAuthenticatedUserId("xiaoming");
        // 启动流程实例
        runtimeService.startProcessInstanceByKey(processKey);
        return Result.success();
    }

}
