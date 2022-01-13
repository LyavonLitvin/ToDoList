package service;

import repository.UserRoleRepository;

import java.util.HashMap;


public class UserRoleServiceImpl {

    private UserRoleRepository userRoleRepository = new UserRoleRepository();

    // получение id роли пользователя
    public int getIdUserRole(String login) {
        return userRoleRepository.getIdUserRole(login);
    }

    // получение id роли ползователя по id пользователя
    public int getIdUserRoleByIdUser(int idUser) {
        return userRoleRepository.getIdUserRoleByIdUser(idUser);
    }

    // получение роли пользователя
    public String getUserRole(int idUserRole) {
        return userRoleRepository.getRoleNameFromIdUserRole(idUserRole);
    }

    // получение списка роли пользователей
    public void printUserRole() {
        HashMap<Integer, String> listUserRole = userRoleRepository.getHashMapUserRoles();
        listUserRole.forEach((k, v) -> {
            System.out.println("ID - " + k + ", Название роли - " + v);
        });
    }

    // проверка на соответсвие роли пользователя
    public boolean isValid(int inputUserIdRole) {
        UserRoleRepository userRoleRepository = new UserRoleRepository();
        int idFromBD = userRoleRepository.getIdUserRole(inputUserIdRole);
        if (idFromBD == -1) {
            return false;
        } else {
            return true;
        }
    }
}

