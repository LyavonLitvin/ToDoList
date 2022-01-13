package entity;

public class TaskStatus {
    private int idTaskStatus;
    private String taskStatus;

    public TaskStatus() {
    }

    public TaskStatus(int idTaskStatus, String taskStatus) {
        this.idTaskStatus = idTaskStatus;
        this.taskStatus = taskStatus;
    }

    public int getIdTaskStatus() {
        return idTaskStatus;
    }

    public void setIdTaskStatus(int idTaskStatus) {
        this.idTaskStatus = idTaskStatus;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
