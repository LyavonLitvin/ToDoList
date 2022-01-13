package repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;

public class TaskCategoryRepository {
    final String url = "jdbc:mysql://localhost:3306/todolistdb?userUnicode=true&serverTimezone=UTC";
    final String username = "root";
    final String password = "admin";

    // запрос в базу на получение списка категорий задач
    public HashMap<Integer, String> getListTaskCategories() {
        HashMap<Integer, String> listTaskCategories = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task_category, name_task_category from task_categories order by id_task_category;");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listTaskCategories.put(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return listTaskCategories;
    }

    // запрос в базу получение id по названию категории
    public int getIdTaskCategoryFromTaskCategory(String nameTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task_category from task_categories where name_task_category = ?;");
                preparedStatement.setString(1, nameTaskCategory);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    return id;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // запрос в базу получение id категории задачи
    public int getIdTaskCategory(int idTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_task_category from task_categories where id_task_category = ?;");
                preparedStatement.setInt(1, idTaskCategory);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    return id;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // запрос в базу на добавление задачи
    public boolean addCategory(String nameTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into task_categories (name_task_category) values (?);");
                preparedStatement.setString(1, nameTaskCategory);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (getIdTaskCategoryFromTaskCategory(nameTaskCategory) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // запрос в базу получение имени категории задачи по id задачи
    public String getNameTaskCategoryFromIdTaskCategory(int idTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select name_task_category from task_categories where id_task_category = ?;");
                preparedStatement.setInt(1, idTaskCategory);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }

    // запрос в базу на удаление категории задачи
    public boolean deleteTaskCategory(int idTaskCategory, int defaultIdTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement updateToDefaultCategory = connection.prepareStatement("update tasks set id_task_category = ? where id_task_category = ?;");
                updateToDefaultCategory.setInt(1, defaultIdTaskCategory);
                updateToDefaultCategory.setInt(2, idTaskCategory);
                updateToDefaultCategory.execute();
                PreparedStatement deleteCategory = connection.prepareStatement("delete from task_categories where id_task_category = ?;");
                deleteCategory.setInt(1, idTaskCategory);
                deleteCategory.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (getIdTaskCategory(idTaskCategory) == -1) {
            return true;
        } else {
            return false;
        }
    }

    // запрос в базу проверку соответствия id и названии категории задачи
    public boolean updateNameTaskCategory(int idTaskCategory, String newNameTaskCategory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update task_categories set name_task_category = ? where id_task_category = ?;");
                preparedStatement.setString(1, newNameTaskCategory);
                preparedStatement.setInt(2, idTaskCategory);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (getIdTaskCategoryFromTaskCategory(newNameTaskCategory) == idTaskCategory) {
            return true;
        } else {
            return false;
        }
    }
}
