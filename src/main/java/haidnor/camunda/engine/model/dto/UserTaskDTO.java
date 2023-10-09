package haidnor.camunda.engine.model.dto;

public class UserTaskDTO {

    private String id;
    private String name;
    private String camundaCandidateUsers;
    private String camundaCandidateGroups;
    private String camundaAssignee;
    private String camundaDueDate;
    private String camundaFollowUpDate;
    private String camundaPriority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCamundaCandidateUsers() {
        return camundaCandidateUsers;
    }

    public void setCamundaCandidateUsers(String camundaCandidateUsers) {
        this.camundaCandidateUsers = camundaCandidateUsers;
    }

    public String getCamundaCandidateGroups() {
        return camundaCandidateGroups;
    }

    public void setCamundaCandidateGroups(String camundaCandidateGroups) {
        this.camundaCandidateGroups = camundaCandidateGroups;
    }

    public String getCamundaAssignee() {
        return camundaAssignee;
    }

    public void setCamundaAssignee(String camundaAssignee) {
        this.camundaAssignee = camundaAssignee;
    }

    public String getCamundaDueDate() {
        return camundaDueDate;
    }

    public void setCamundaDueDate(String camundaDueDate) {
        this.camundaDueDate = camundaDueDate;
    }

    public String getCamundaFollowUpDate() {
        return camundaFollowUpDate;
    }

    public void setCamundaFollowUpDate(String camundaFollowUpDate) {
        this.camundaFollowUpDate = camundaFollowUpDate;
    }

    public String getCamundaPriority() {
        return camundaPriority;
    }

    public void setCamundaPriority(String camundaPriority) {
        this.camundaPriority = camundaPriority;
    }
}
