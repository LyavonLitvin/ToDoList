package entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Task {
    private int idTask;
    private int idTaskCreator;
    private int idTaskExecutor;
    private int idTaskCategory;
    private int idTaskStatus;
    private String taskTopic;
    private Timestamp taskDateUpdate;
    private String taskDescription;


    public Task(int idTaskCreator, int idTaskExecutor, int idTaskCategory,
                int idTaskStatus, String taskTopic, Timestamp taskDateUpdate, String taskDescription) {
        this.idTaskCreator = idTaskCreator;
        this.idTaskExecutor = idTaskExecutor;
        this.idTaskCategory = idTaskCategory;
        this.idTaskStatus = idTaskStatus;
        this.taskTopic = taskTopic;
        this.taskDateUpdate = taskDateUpdate;
        this.taskDescription = taskDescription;
    }

    public Task(int idTask, int idTaskCreator, int idTaskExecutor, int idTaskCategory,
                int idTaskStatus, String taskTopic, Timestamp taskDateUpdate, String taskDescription) {
        this.idTask = idTask;
        this.idTaskCreator = idTaskCreator;
        this.idTaskExecutor = idTaskExecutor;
        this.idTaskCategory = idTaskCategory;
        this.idTaskStatus = idTaskStatus;
        this.taskTopic = taskTopic;
        this.taskDateUpdate = taskDateUpdate;
        this.taskDescription = taskDescription;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getIdTaskCreator() {
        return idTaskCreator;
    }

    public void setIdTaskCreator(int idTaskCreator) {
        this.idTaskCreator = idTaskCreator;
    }

    public int getIdTaskExecutor() {
        return idTaskExecutor;
    }

    public void setIdTaskExecutor(int idTaskExecutor) {
        this.idTaskExecutor = idTaskExecutor;
    }

    public int getIdTaskCategory() {
        return idTaskCategory;
    }

    public void setIdTaskCategory(int idTaskCategory) {
        this.idTaskCategory = idTaskCategory;
    }

    public int getIdTaskStatus() {
        return idTaskStatus;
    }

    public void setIdTaskStatus(int idTaskStatus) {
        this.idTaskStatus = idTaskStatus;
    }

    public String getTaskTopic() {
        return taskTopic;
    }

    public void setTaskTopic(String taskTopic) {
        this.taskTopic = taskTopic;
    }

    public Timestamp getTaskDateUpdate() {
        return taskDateUpdate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void setTaskDateUpdate(LocalDateTime taskDateUpdate) {
        this.taskDateUpdate = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

}
