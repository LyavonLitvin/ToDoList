package repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;

public class TaskStatusRepository {
    final String url = "jdbc:mysql://localhost:3306/todolistdb?useUnicode=true&serverTimezone=UTC";
    final String username = "root";
    final String password = "admin";

    // запрос в базу данных на получение списка статусов задач
    public HashMap<Integer, String> getListTaskStatuses() {
        HashMap<Integer, String> listTaskStatus = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from task_statuses order by id_task_status");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listTaskStatus.put(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return listTaskStatus;
    }

    // запрос в базу данных на получение id статуса задачи
    public int getIdTaskStatus(int idTaskStatus) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select task_statuses.id_task_status from task_statuses where id_task_status = ?;");
                preparedStatement.setInt(1, idTaskStatus);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // запрос в базу данных на получение статуса задачи по id задачи
    public int getIdTaskStatusByIdTask(int idTask) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select tasks.id_task_status from tasks where id_task = ?;");
                preparedStatement.setInt(1, idTask);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // запрос в базу данных на получение id статуса задачи по названию статуса задачи
    public int getIdTaskStatusFromTaskStatus(String taskStatus) { // Получение id статуса задачи по названию статуса задачи
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select task_statuses.id_task_status from task_statuses where task_status = ?;");
                preparedStatement.setString(1, taskStatus);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
