package haidnor.camunda.engine.service;

import org.camunda.bpm.engine.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaHistoryService {

    @Autowired
    private HistoryService historyService;

}
