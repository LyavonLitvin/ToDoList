package service;

import entity.Task;
import repository.TaskCategoryRepository;
import repository.TaskRepository;
import repository.TaskStatusRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskServiceImpl {
    private TaskRepository taskRepository = new TaskRepository();
    private TaskStatusRepository taskStatusRepository = new TaskStatusRepository();
    private TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
    private Task task;

    // получение списка задач
    public void listUserTasks(int idUser) {
        ArrayList<String> listUserTasks = taskRepository.getListTasksFromIdUser(idUser);
        System.out.println("\nСписок заданий пользователя:\n");
        listUserTasks.forEach(System.out::println);
    }

    // получение id статуса задачи
    public int getTaskStatusId(int statusId) {
        return taskStatusRepository.getIdTaskStatus(statusId);
    }

    // получение информации о задаче по id пользователя и id задачи
    public String getTaskInfoByIdUserAndIdTask(int idUser, int idTask) {
        String taskInfo = taskRepository.getTaskInfoFromIdUserAndIdTask(idUser, idTask);
        if (taskInfo.length() == 0) {
            return "Проверьте корректность вводимых данных";
        }
        return taskInfo;
    }

    // получение id задачи по id задачи
    public int getIdTask(int idTask) {
        int idTaskDB = taskRepository.getIdTask(idTask);
        return idTaskDB;
    }

    // получение списка статуса задач
    public void listTaskStatus() {
        HashMap<Integer, String> listTaskStatus = taskStatusRepository.getListTaskStatuses();
        listTaskStatus.forEach((k, v) -> {
            System.out.println("ID - " + k + ", Название статуса - " + v);
        });
    }

    // обновление статуса задачи
    public int updateTaskStatus(int idTask, int idStatus) {
        int resultUpdate = taskRepository.updateTaskStatusByIdTaskAndIdTaskStatus(idTask, idStatus);
        return resultUpdate;
    }

    // получение информации о задаче
    public String getTaskInfo(int taskId) {
        return taskRepository.getTaskInfoByIdTask(taskId);
    }

    // получение id статуса задачи по статусу задачи
    public int getIdTaskStatusFromTaskStatus(String taskStatus) {
        int idStatus = taskStatusRepository.getIdTaskStatusFromTaskStatus(taskStatus);
        return idStatus;
    }

    // получение id категории задачи по категории задачи
    public int getIdTaskCategoryFromNameTaskCategory(String nameTaskCategory) {
        int idCategory = taskCategoryRepository.getIdTaskCategoryFromTaskCategory(nameTaskCategory);
        return idCategory;
    }

    // получение id категории задачи
    public int getIdTaskCategory(int idTaskCategory) {
        return taskCategoryRepository.getIdTaskCategory(idTaskCategory);
    }

    // получение информации о всех задачах
    public void printTasksInfo() {
        ArrayList<String> listTasksInfo = taskRepository.getTasks();
        System.out.println("\nСписок всех задач:");
        listTasksInfo.forEach(System.out::println);
    }

    // получение списка категорий
    public void printListCategory() {
        HashMap<Integer, String> listCategory = taskCategoryRepository.getListTaskCategories();
        System.out.println("\nСписок всех категорий:");
        listCategory.forEach((k, v) -> {
            System.out.println("ID - " + k + ", название категории - " + v);
        });
    }

    // добавление задачи в БД
    public boolean addTaskToDB(int idTaskCreator, int idTaskExecutor, int idTaskCategory,
                               int idTaskStatus, String taskTopic, Timestamp taskDateUpdate, String taskDescription) {
        task = new Task(idTaskCreator, idTaskExecutor, idTaskCategory, idTaskStatus, taskTopic, taskDateUpdate, taskDescription);
        return taskRepository.addTask(task);
    }

    // удаление задачи
    public boolean deleteTaskByIdTask(int idTask) {
        return taskRepository.deleteTaskByIdTask(idTask);
    }

    // обновление названия задачи
    public boolean updateTaskTopic(int idTask, String newTaskTopic) {
        return taskRepository.updateTaskTopic(idTask, newTaskTopic);
    }

    // обновление описания задачи
    public boolean updateTaskDescription(int idTask, String newTaskDescription) {
        return taskRepository.updateTaskDescription(idTask, newTaskDescription);
    }

    // обновление исполнителя задачи
    public boolean updateUserIdForTask(int idTask, int idUser) {
        return taskRepository.updateIdUserByIdTaskAndIdUser(idTask, idUser);
    }

    // обновление категории задачи
    public boolean updateIdTaskCategoryFromIdTask(int idTask, int idTaskCategory) {
        return taskRepository.updateIdTaskCategoryByIdTaskAndIdTaskCategory(idTask, idTaskCategory);
    }

    // добаление категории задач
    public boolean addCategory(String nameTaskCategory) {
        return taskCategoryRepository.addCategory(nameTaskCategory);
    }

    // получение названия категории задачи по id категории задачи
    public String getNameTaskCategoryFromIdTaskCategory(int idTaskCategory) {
        return taskCategoryRepository.getNameTaskCategoryFromIdTaskCategory(idTaskCategory);
    }

    // удаление категории
    public boolean deleteCategory(int idTaskCategory, int defaultIdTaskCategory) {
        return taskCategoryRepository.deleteTaskCategory(idTaskCategory, defaultIdTaskCategory);
    }

    // обновление названия категории задач
    public boolean updateNameTaskCategory(int idTaskCategory, String newNameCategory) {
        return taskCategoryRepository.updateNameTaskCategory(idTaskCategory, newNameCategory);
    }
}
