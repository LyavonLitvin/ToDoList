package repository;

import entity.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserRepository {
    final String url = "jdbc:mysql://localhost:3306/todolistdb?useUnicode=true&serverTimezone=UTC";
    final String username = "root";
    final String password = "admin";

    // запрос в базу данных на добавление пользователя
    public int addUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id_user_role, user_login, user_password, user_phone_number, user_email," +
                        " user_first_name, user_last_name, user_age, user_date_update) values (?,?,?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, user.getIdUserRole());
                preparedStatement.setString(2, user.getUserLogin());
                preparedStatement.setString(3, user.getUserPassword());
                preparedStatement.setString(4, user.getUserPhoneNumber());
                preparedStatement.setString(5, user.getUserEmail());
                preparedStatement.setString(6, user.getUserFirstName());
                preparedStatement.setString(7, user.getUserLastName());
                preparedStatement.setInt(8, user.getUserAge());
                preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        int userID = getIdUser(user);
        if (userID != -1) {
            return userID;
        } else {
            return -1;
        }
    }

    // запрос в базу данных на обновление пользователя
    public int updateUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set id_user_role = ?, user_login = ?, user_password = ?, user_phone_number = ?, user_email = ?," +
                        " user_first_name = ?, user_last_name = ?, user_age = ?, user_date_update =? where id_user = ?;");
                preparedStatement.setInt(1, user.getIdUserRole());
                preparedStatement.setString(2, user.getUserLogin());
                preparedStatement.setString(3, user.getUserPassword());
                preparedStatement.setString(4, user.getUserPhoneNumber());
                preparedStatement.setString(5, user.getUserEmail());
                preparedStatement.setString(6, user.getUserFirstName());
                preparedStatement.setString(7, user.getUserLastName());
                preparedStatement.setInt(8, user.getUserAge());
                preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(10, user.getIdUser());
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        int userID = getIdUser(user);
        if (userID != -1) {
            return userID;
        } else {
            return -1;
        }
    }

    // запрос в базу данных на обновление логина пользователя
    public int updateUserLogin(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set user_login = ?, user_date_update = ? where id_user = ?;");
                preparedStatement.setString(1, user.getUserLogin());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, user.getIdUser());
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        // -1 - если юзер не обновился, значит и не найден в БД, иначе возврат id
        int userID = getIdUser(user.getUserLogin());
        if (userID != -1) {
            return userID;
        } else {
            return -1;
        }
    }

    // запрос в базу данных на обновление логина пользователя по id пользователя
    public int updateUserLogin(int idUser, String newLogin) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set user_login = ?, user_date_update = ? where id_user = ?;");
                preparedStatement.setString(1, newLogin);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        int userID = getIdUser(idUser);
        if (userID != -1) {
            return userID;
        } else {
            return -1;
        }
    }

    // запрос в базу данных на обновление пароля пользователя по id пользователя
    public int updateUserPassword(int idUser, String newUserPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set user_password = ?, user_date_update = ? where id_user = ?;");
                preparedStatement.setString(1, newUserPassword);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        int userID = getIdUser(idUser);
        if (userID != -1) {
            return userID;
        } else {
            return -1;
        }
    }

    // запрос в базу данных на обновление роли пользователя по id пользователя
    public boolean updateUserRole(int idUser, int newIdUserRole) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set id_user_role = ?, user_date_update = ? where id_user = ?;");
                preparedStatement.setInt(1, newIdUserRole);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(3, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (checkUserRole(idUser, newIdUserRole)) {
            return true;
        } else {
            return false;
        }
    }

    // запрос в базу данных на удаление пользователя по id пользователя
    public boolean deleteUser(int idUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id_user = ?");
                preparedStatement.setInt(1, idUser);
                preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        int userID = getIdUser(idUser);
        if (userID == -1) {
            return true;
        } else {
            return false;
        }
    }

    // запрос в базу данных на получения id пользователя по логину пользователя
    public int getIdUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select users.id_user from users where user_login = ?");
                preparedStatement.setString(1, user.getUserLogin());
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

    // запрос в базу данных на получения id пользователя по логину пользователя
    public int getIdUser(String login) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select users.id_user from users where user_login = ?");
                preparedStatement.setString(1, login);
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

    // запрос в базу данных на получения id пользователя по id пользователя
    public int getIdUser(int IdUser) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select users.id_user from users where id_user = ?");
                preparedStatement.setInt(1, IdUser);
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

    // запрос в базу данных на проверку роли пользователя по id пользователя и по id роли
    public boolean checkUserRole(int idUser, int idUserRole) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select id_user, id_user_role from users where id_user = ?;");
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int idUserDB = resultSet.getInt(1);
                    int idUserRoleDB = resultSet.getInt(2);
                    if (idUser == idUserDB && idUserRole == idUserRoleDB)
                        return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на проверку логина и пароля
    public boolean checkUserPassword(String tempLogin, String tempPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select user_login, user_password from users where user_login = ?");
                preparedStatement.setString(1, tempLogin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String userLoginDB = resultSet.getString(1);
                    String userPasswordDB = resultSet.getString(2);
                    if (tempLogin.equals(userLoginDB) && tempPassword.equals(userPasswordDB))
                        return true;
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    // запрос в базу данных на получения информации о пользователе
    public String getUserInfo(int idUser) {
        String userInfo = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select users.id_user, user_roles.user_role, users.user_login, users.user_password, user_phone_number, user_email, user_first_name, user_last_name, user_age, user_date_update from users " +
                        "inner join user_roles on users.id_user_role = user_roles.id_user_role where users.id_user = ?;");
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    userInfo = "id - " + resultSet.getInt(1) +
                            ", роль пользователя - " + resultSet.getString(2) +
                            ", логин - " + resultSet.getString(3) +
                            ", пароль - " + resultSet.getString(4) +
                            ", телефон - " + resultSet.getString(5) +
                            ", @mail - " + resultSet.getString(6) +
                            ", имя - " + resultSet.getString(7) +
                            ", фамилия - " + resultSet.getString(8) +
                            ", возраст - " + resultSet.getInt(9) +
                            ", дата последнего обновления карточки - " + resultSet.getDate(10);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // запрос в базу данных на получения информации об авторизованном пользователе
    public User getAuthorizedUserInfo(int idUser) {
        int tempIdUser = -1;
        int tempIdUserRole = -1;
        String tempIdUserLogin = "";
        String tempIdUserPassword = "";
        String tempUserPhoneNumber = "";
        String tempUserEmail = "";
        String tempUserFirstName = "";
        String tempUserLastName = "";
        int tempUserAge = 0;
        Timestamp tempUserDateUpdate = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select users.id_user, users.id_user_role, users.user_login, users.user_password, user_phone_number, user_email, user_first_name, user_last_name, user_age, user_date_update from users " +
                        " where users.id_user = ?;");
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    tempIdUser = resultSet.getInt(1);
                    tempIdUserRole = resultSet.getInt(2);
                    tempIdUserLogin = resultSet.getString(3);
                    tempIdUserPassword = resultSet.getString(4);
                    tempUserPhoneNumber = resultSet.getString(5);
                    tempUserEmail = resultSet.getString(6);
                    tempUserFirstName = resultSet.getString(7);
                    tempUserLastName = resultSet.getString(8);
                    tempUserAge = resultSet.getInt(9);
                    tempUserDateUpdate = resultSet.getTimestamp(10);
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new User(tempIdUser, tempIdUserRole, tempIdUserLogin, tempIdUserPassword, tempUserPhoneNumber, tempUserEmail, tempUserFirstName, tempUserLastName, tempUserAge, tempUserDateUpdate);
    }

    // запрос в базу данных на получения информации о всех пользователях
    public ArrayList<String> getUsersInfo() {
        ArrayList<String> listUsersInfo = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "select users.id_user, user_roles.user_role, users.user_login, users.user_password, user_phone_number, user_email, user_first_name, user_last_name, user_age, user_date_update from users " +
                        "inner join user_roles on users.id_user_role = user_roles.id_user_role order by users.id_user;");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listUsersInfo.add("id - " + resultSet.getInt(1) +
                            ", роль пользователя - " + resultSet.getString(2) +
                            ", логин - " + resultSet.getString(3) +
                            ", пароль - " + resultSet.getString(4) +
                            ", телефон - " + resultSet.getString(5) +
                            ", @mail - " + resultSet.getString(6) +
                            ", имя - " + resultSet.getString(7) +
                            ", фамилия - " + resultSet.getString(8) +
                            ", возраст - " + resultSet.getString(9) +
                            ", дата последнего обновления карточки - " + resultSet.getDate(10));
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return listUsersInfo;
    }


}
