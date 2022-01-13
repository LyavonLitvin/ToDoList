package service;

import entity.User;
import repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserServiceImpl {
    private UserRepository userRepository = new UserRepository();
    private User user;

    // добавление пользователя в базу данных
    public boolean addUserToDB(int idUserRole, String userLogin, String userPassword, String userPhoneNumber,
                               String userEmail, String userFirstName, String userLastName, int userAge) {
        user = new User(idUserRole, userLogin, userPassword, userPhoneNumber, userEmail, userFirstName, userLastName, userAge, Timestamp.valueOf(LocalDateTime.now()));
        int userResultAddFromBD = userRepository.addUser(user);
        if (userResultAddFromBD != -1) {
            user.setIdUser(userResultAddFromBD);
            return true;
        }
        return false;
    }

    // обновление пользователя в базу данных
    public boolean updateUserToDB(int idUser, int idUserRole, String userLogin, String userPassword, String userPhoneNumber,
                               String userEmail, String userFirstName, String userLastName, int userAge) {
        user = new User(idUser, idUserRole, userLogin, userPassword, userPhoneNumber, userEmail, userFirstName, userLastName, userAge, Timestamp.valueOf(LocalDateTime.now()));
        int userResultUpdateFromBD = userRepository.updateUser(user);
        if (userResultUpdateFromBD != -1) {
            user.setIdUser(userResultUpdateFromBD);
            return true;
        }
        return false;
    }

    // удаление пользователя
    public boolean deleteUser(int idUser) {
        return userRepository.deleteUser(idUser);
    }

    // создание авторизованного пользователя
    public void createAuthorizedUser(int idUser) {
        user = userRepository.getAuthorizedUserInfo(idUser);
        user.setIdUser(idUser);
    }

    // получение id авторизованного пользователя
    public int getAuthorizedUserId() {
        return user.getIdUser();
    }

    // получение роли авторизованного пользователя
    public int getAuthorizedUserRole() {
        return user.getIdUserRole();
    }

    // получения id пользователя по логину
    public int getIdUser(String login) {
        return userRepository.getIdUser(login);
    }

    // получения id пользователя по id
    public int getIdUser(int userId) {
        return userRepository.getIdUser(userId);
    }

    // проверка обновления логина авторизованного пользователя
    public boolean checkUpdateAuthorizedUserLogin(String newUserLogin) {
        user.setUserLogin(newUserLogin);
        if (userRepository.updateUserLogin(user) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // проверка обновления логина пользователя
    public boolean checkUpdateUserLogin(int userId, String newLogin) {
        if (userRepository.updateUserLogin(userId, newLogin) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // проверка обновления пароля пользователя
    public boolean checkUpdateUserPassword(int idUser, String newPassword) {
        if (userRepository.updateUserPassword(idUser, newPassword) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // получение информации по пользователю
    public String getUserInfo(int userId) {
        return userRepository.getUserInfo(userId);
    }

    // получение списка пользователей
    public void printUsersInfo() {
        ArrayList<String> listUsersInfo = userRepository.getUsersInfo();
        System.out.println("\nСписок всех пользователей:");
        listUsersInfo.forEach(System.out::println);
    }

    // проверка изменения роли пользователя
    public boolean checkUpdateUserRole(int userId, int newRoleId) {
        boolean result = userRepository.updateUserRole(userId, newRoleId);
        return result;
    }

    // проверка пароля в БД
    public boolean checkUserPasswordInDB(String inputUserLogin, String inputUserPassword) {
        if (userRepository.checkUserPassword(inputUserLogin, inputUserPassword)) {
            return true;
        } else {
            return false;
        }
    }

    // проверка пароля в БД
    public boolean checkUserLoginToDB(String inputUserLogin) {
        if (getIdUser(inputUserLogin) != -1) {
            return true;
        } else {
            return false;
        }
    }
}
