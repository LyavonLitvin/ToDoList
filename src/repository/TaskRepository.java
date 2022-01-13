package repository;

import entity.Task;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskRepository {
    final String url = "jdbc:mysql://localhost:3306/todolistdb?useUnicode=true&serverTimezone=UTC";
    final String username = "root";
    final String password = "admin";

    TaskStatusRepository taskStatusRepository = new TaskStatusRepository();

    // запрос в базу данных на наличие задачи по id задачи
    public int getIdTask(int idTask) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select tasks.id_task from tasks where id_task = ?;");
                preparedStatement.setInt(1, idTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    return id;
                } else {
                    return -1;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // запрос в базу данных получение списка задач
    public ArrayList<String> getListTasksFromIdUser(int IdUser) { // Просмотр задач назначенных пользователю
        ArrayList<String> listTasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select id_task, task_categories.name_task_category, task_statuses.task_status, task_topic, task_date_update, task_description  from tasks " +
                        "inner join task_statuses on tasks.id_task_status = task_statuses.id_task_status " +
                        "inner join task_categories on tasks.id_task_category = task_categories.id_task_category " +
                        "where tasks.id_task_executor = ? order by id_task;");
                preparedStatement.setInt(1, IdUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listTasks.add("id - " + resultSet.getInt(1) +
                            ", Категория - " + resultSet.getString(2) +
                            ", Статус - " + resultSet.getString(3) +
                            ", Заголовок - " + resultSet.getString(4) +
                            ", Дата последнего обновления - " + resultSet.getDate(5) +
                            ", Описание - " + resultSet.getString(6));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return listTasks;
    }

    // запрос в базу данных на получение информации по задаче
    public String getTaskInfoByIdTask(int IdTask) {
        String taskInfo = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select tasks.id_task, u.user_last_name, us.user_last_name, task_categories.name_task_category, " +
                        "task_statuses.task_status, task_topic, task_date_update, task_description from tasks " +
                        "inner join users u on tasks.id_task_creator = u.id_user " +
                        "inner join users us on tasks.id_task_executor = us.id_user " +
                        "inner join task_statuses on tasks.id_task_status = task_statuses.id_task_status " +
                        "inner join task_categories on tasks.id_task_category = task_categories.id_task_category " +
                        "where tasks.id_task = ?");
                preparedStatement.setInt(1, IdTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    taskInfo = "id - " + resultSet.getInt(1) +
                            ", Владелец задачи - " + resultSet.getString(2) +
                            ", Исполнитель задачи - " + resultSet.getString(3) +
                            ", Категория - " + resultSet.getString(4) +
                            ", Статус - " + resultSet.getString(5) +
                            ", Заголовок - " + resultSet.getString(6) +
                            ", Дата последнего обновления - " + resultSet.getDate(7) +
                            ", Описание - " + resultSet.getString(8);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return taskInfo;
    }

    // запрос в базу данных на списка всех задач
    public ArrayList<String> getTasks() {
        ArrayList<String> listTasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select id_task, users.user_last_name, users.user_last_name, task_categories.name_task_category, task_statuses.task_status, task_topic, task_date_update, task_description from tasks " +
                        "inner join users on tasks.id_task_executor = users.id_user " +
                        "inner join task_statuses on tasks.id_task_status = task_statuses.id_task_status " +
                        "inner join task_categories on tasks.id_task_category = task_categories.id_task_category " +
                        "order by task_id;");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listTasks.add("id - " + resultSet.getInt(1) +
                            ", Владелец задачи - " + resultSet.getString(2) +
                            ", Исполнитель задачи - " + resultSet.getString(3) +
                            ", Категория - " + resultSet.getString(4) +
                            ", Статус - " + resultSet.getString(5) +
                            ", Заголовок - " + resultSet.getString(6) +
                            ", Дата последнего обновления - " + resultSet.getDate(7) +
                            ", Описание - " + resultSet.getString(8));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return listTasks;
    }

    // запрос в базу данных на проверку наличия задачи с выбранной темой задачи и id пользователя
    public boolean checkTaskTopicByTaskTopicAndIdUser(String taskTopic, int idUser) { //
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task from tasks where id_task_executor = ? and task_topic = ?;");
                preparedStatement.setInt(1, idUser);
                preparedStatement.setString(2, taskTopic);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на проверку наличия задачи с выбранной темой задачи и id пользователя
    public boolean checkTaskByTaskTopicAndIdTask(String taskTopic, int idTask) { //
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task from tasks where id_task = ? and task_topic = ?;");
                preparedStatement.setString(1, taskTopic);
                preparedStatement.setInt(2, idTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на проверку наличия задачи с выбранной id задачи и id пользователя
    public boolean checkTaskByIdTaskAndIdUser(int idTask, int idUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task from tasks where id_task = ? and id_task_executor = ?;");
                preparedStatement.setInt(1, idTask);
                preparedStatement.setInt(2, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на проверку наличия задачи с выбранной id задачи и id категории задачи
    public boolean checkTaskByIdTaskAndIdTaskCategory(int idTask, int idTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task from tasks where id_task = ? and id_task_category = ?;");
                preparedStatement.setInt(1, idTask);
                preparedStatement.setInt(2, idTaskCategory);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на добавление задачи
    public boolean addTask(Task task) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into tasks (id_task_creator, id_task_executor, id_task_category, id_task_status, task_topic," +
                        "task_date_update, task_description) values (?,?,?,?,?,?,?);");
                preparedStatement.setInt(1, task.getIdTaskCreator());
                preparedStatement.setInt(2, task.getIdTaskExecutor());
                preparedStatement.setInt(3, task.getIdTaskCategory());
                preparedStatement.setInt(4, task.getIdTaskStatus());
                preparedStatement.setString(5, task.getTaskTopic());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(7, task.getTaskDescription());
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return checkTaskTopicByTaskTopicAndIdUser(task.getTaskTopic(), task.getIdTaskExecutor());
    }

    // запрос в базу данных на удаление задачи с выбранной id задачи
    public boolean deleteTaskByIdTask(int idTask) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from tasks where id_task = ?;");
                preparedStatement.setInt(1, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (getIdTask(idTask) == -1) {
            return true;
        } else {
            return false;
        }
    }

    // запрос в базу данных на обновление темы задачи задачи с выбранной id задачи
    public boolean updateTaskTopic(int idTask, String newTaskTopic) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update tasks set task_topic = ?, task_date_update = ? where id_task = ?;");
                preparedStatement.setString(1, newTaskTopic);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return checkTaskByTaskTopicAndIdTask(newTaskTopic, idTask);
    }

    // запрос в базу данных на обновление описание задачи задачи с выбранной id задачи
    public boolean updateTaskDescription(int idTask, String newTaskDescription) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update tasks set task_description = ?, task_date_update = ? where id_task = ?;");
                preparedStatement.setString(1, newTaskDescription);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return checkTaskDescriptionByIdTask(idTask, newTaskDescription);
    }

    // запрос в базу данных на проверку обновления темы задачи задачи с выбранной id задачи
    public boolean checkTaskDescriptionByIdTask(int idTask, String TaskDescription) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task from tasks where id_task = ? and task_description = ?;");
                preparedStatement.setInt(1, idTask);
                preparedStatement.setString(2, TaskDescription);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на обновление исполнителя задачи с выбранной id задачи
    public boolean updateIdUserByIdTaskAndIdUser(int idTask, int idUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update tasks set id_task_executor = ?, task_date_update = ? where id_task = ?;");
                preparedStatement.setInt(1, idUser);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return checkTaskByIdTaskAndIdUser(idTask, idUser);
    }

    // запрос в базу данных на обновление категории задачи с выбранной id задачи
    public boolean updateIdTaskCategoryByIdTaskAndIdTaskCategory(int taskId, int categoryId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update tasks set id_task_category = ?, task_date_update = ? where id_task = ?;");
                preparedStatement.setInt(1, categoryId);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, taskId);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return checkTaskByIdTaskAndIdTaskCategory(taskId, categoryId);
    }

    // запрос в базу данных на получение информации о задаче с выбранной id задачи и id исполнителя задачи
    public String getTaskInfoFromIdUserAndIdTask(int idUser, int idTask) {
        String taskInfo = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select tasks.id_task, tasks.task_topic, task_statuses.task_status, task_categories.name_task_category, tasks.task_description, tasks.task_date_update from tasks " +
                        "inner join task_statuses on tasks.id_task_status = task_statuses.id_task_status " +
                        "inner join task_categories on tasks.id_task_category = task_categories.id_task_category where tasks.id_task_executor = ? and tasks.id_task = ?;");
                preparedStatement.setInt(1, idUser);
                preparedStatement.setInt(2, idTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    taskInfo = "id - " + resultSet.getInt(1) +
                            ", название - " + resultSet.getString(2) +
                            ", статус - " + resultSet.getString(3) +
                            ", категория - " + resultSet.getString(4) +
                            ", описание - " + resultSet.getString(5) +
                            ", дата последнего обновления задачи - " + resultSet.getDate(6);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return taskInfo;
    }

    // запрос в базу данных на обновление статуса задачи с выбранной id задачи
    public int updateTaskStatusByIdTaskAndIdTaskStatus(int idTask, int idTaskStatus) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update tasks set id_task_status = ?, task_date_update = ?  where id_task = ?;");
                preparedStatement.setInt(1, idTaskStatus);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idTask);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (taskStatusRepository.getIdTaskStatusByIdTask(idTask) == idTaskStatus) {
            return idTaskStatus;
        }
        return -1;
    }
}
