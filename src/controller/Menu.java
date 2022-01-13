package controller;

import entity.TaskCategory;
import validator.Console;
import service.UserRoleServiceImpl;
import service.TaskServiceImpl;
import service.UserServiceImpl;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Menu {
    private Console console = new Console();
    private TaskCategory taskCategory = new TaskCategory();
    private UserRoleServiceImpl userRoleServiceImpl = new UserRoleServiceImpl();
    private TaskServiceImpl taskServiceImpl = new TaskServiceImpl();
    private UserServiceImpl userServiceImpl = new UserServiceImpl();

    private String inputUserItemMenu = "";

    // основное меню
    public void start() {
        showTitle();
        showMenuAuthorizationOrRegistration();
    }

    // приветственное меню
    public void showTitle() {
        System.out.println("Добро пожаловать в Базу данных задач для сотрудников");
    }

    // меню выбора авторизации или регистрации
    public void showMenuAuthorizationOrRegistration() {
        while (true) {
            System.out.println("Для продолжения работы необхожимо авторизоваться или зарегистрироваться\n Выберите пункт меню:\n Нажмите 1 для авторизации." +
                    "\n Нажмите 2 для регистрации.\n Нажмите 3 для завершения работы с программой.");
            inputUserItemMenu = console.scanItemMenu();
            if (inputUserItemMenu.equals("1")) {
                showAuthorizationMenuTitle();
                showAuthorizationMenu();
            } else if (inputUserItemMenu.equals("2")) {
                showRegistrationMenuTitle();
                showRegistrationMenu();
            } else if (inputUserItemMenu.equals("3")) {
                System.out.println("Вы выбрали завершение программы. До свидания!)");
                console.closeScanner();
                System.exit(0);
            } else {
                System.out.println("\nТакого пункта меню не существует. Выберите только из указанных вариантов.");
            }
        }
    }

    // Приветственнное сообщение меню авторизации
    public void showAuthorizationMenuTitle() { // приветственное сообщение авторизации
        System.out.println("Вы вошли в меню авторизации:");
    }

    // меню авторизации
    public void showAuthorizationMenu() {
        while (true) {
            System.out.println("Выберите пункт меню:\n Нажмите 1 для ввода логина и пароля.\n Нажмите 2 для возврата в предыдущее меню");
            inputUserItemMenu = console.scanItemMenu();
            if (inputUserItemMenu.equals("1")) {
                showAuthorizationMainMenu();
            } else if (inputUserItemMenu.equals("2")) {
                System.out.println("Вы выбрали вернуться в предвыдущее меню");
                showMenuAuthorizationOrRegistration();
            } else {
                System.out.println("\nТакого пункта меню не существует. Выберите только из указанных вариантов");
            }
        }
    }

    // основное меню авторизации
    private void showAuthorizationMainMenu() {
        String inputUserLogin = "";
        String inputUserPassword = "";
        int idUser = -1;
        int idUserRole = -1;

        boolean loginIsValid = false;    //валидация пройдена, логин есть в базе
        boolean passwordIsValid = false; //валидация пройдена, пароль не проверен в базе

        while (true) {
            System.out.println("\nВведите логин пользователя:");
            inputUserLogin = console.scanLogin();
            if (!userServiceImpl.checkUserLoginToDB(inputUserLogin)) {
                System.out.println("\nПользователь с введенным логином не существует!");
                showAuthorizationMenu();
            } else {
                loginIsValid = true;
                break;
            }
        }

        while (loginIsValid) { // проверка пароля в базе данных
            System.out.println("\nВведите пароль:");
            inputUserPassword = console.scanPassword();

            if (!userServiceImpl.checkUserPasswordInDB(inputUserLogin, inputUserPassword)) {
                System.out.println("\nПароль введен не верно");
                showAuthorizationMenu();
            } else {
                passwordIsValid = true;
                break;
            }
        }

        if (loginIsValid && passwordIsValid) {
            idUser = userServiceImpl.getIdUser(inputUserLogin);
            idUserRole = userRoleServiceImpl.getIdUserRole(inputUserLogin);

            userServiceImpl.createAuthorizedUser(idUser);

            if (userRoleServiceImpl.getUserRole(idUserRole).toLowerCase().equals("user")) {
                showUserMenuTitle();
                showUserMenu(idUser);
            } else if (userRoleServiceImpl.getUserRole(idUserRole).toLowerCase().equals("manager")) {
                showManagerMenuTitle();
                showManagerMenu(idUser);
            }
        }
    }

    // приветственное сообщение регистрации
    public void showRegistrationMenuTitle() {
        System.out.println("Вы вошли в меню регистрации:");
    }

    // меню регистрации
    public void showRegistrationMenu() {
        while (true) {
            do {
                System.out.println("Выберите пункт меню:\n Нажмите 1 для регистрации.\n Нажмите 2 для возврата в предыдущее меню");
                inputUserItemMenu = console.scanItemMenu();
                if (inputUserItemMenu.equals("1")) {
                    showRegistrationMainMenu();
                } else if (inputUserItemMenu.equals("2")) {
                    System.out.println("Вы выбрали вернуться в предвыдущее меню");
                    showMenuAuthorizationOrRegistration();
                } else {
                    System.out.println("\nТакого пункта меню не существует. Выберите только из указанных вариантов");
                }
            } while (!inputUserItemMenu.equals("2"));
        }
    }

    // основное меню регистрации
    public void showRegistrationMainMenu() {
        String inputUserLogin = "";
        String inputUserPassword = "";
        int idUserRole = -1;
        String userPhoneNumber = "";
        String userEmail = "";
        String userFirstName = "";
        String userLastName = "";
        int userAge = 0;
        String roleIdString = "";
        boolean loginIsFree = false;
        boolean passwordIsValid = false;

        while (true) {
            System.out.println("Введите логин пользователя:");

            inputUserLogin = console.scanLogin();
            if (userServiceImpl.checkUserLoginToDB(inputUserLogin)) {
                System.out.println("\nВведенный логин пользователя уже занят!\n");
            } else {
                loginIsFree = true;
                break;
            }
        }

        while (loginIsFree) {
            System.out.println("\nВведите пароль:");
            inputUserPassword = console.scanPassword();
            passwordIsValid = true;
            break;
        }

        while (loginIsFree && passwordIsValid) {
            System.out.println("\nВыберите id роли пользователя:");
            userRoleServiceImpl.printUserRole();
            System.out.println("\nВведите необходимый id роли");

            roleIdString = console.scanItemMenu();
            if (console.isNumeric(roleIdString)) {
                idUserRole = Integer.parseInt(roleIdString);
            } else {
                System.out.println("\nВведенное id не существует");
                continue;
            }

            if (!userRoleServiceImpl.isValid(idUserRole)) {
                System.out.println("\nВведенное id не существует");
            } else {
                break;
            }
        }
        userPhoneNumber = console.scanPhoneNumber();
        userEmail = console.scanEmail();
        System.out.println("Введите ваше имя: ");
        userFirstName = console.scanFirstName();
        System.out.println("Введите вашу фамилию: ");
        userLastName = console.scanLastName();
        System.out.println("Введите ваш возраст: ");
        userAge = console.scanAge();

        if (userServiceImpl.addUserToDB(idUserRole, inputUserLogin, inputUserPassword, userPhoneNumber, userEmail, userFirstName, userLastName, userAge)) {
            System.out.println("\nПользователь успешно создан");
            showMenuAuthorizationOrRegistration();
        } else {
            System.out.println("\nОшибка во время добавления пользователя");
        }
    }

    // меню для работы менеджера
    private void showManagerMenu(int idTaskCreator) {
        do {
            System.out.println("\n1 - вывод данных текущего пользователя");
            System.out.println("2 - вывод всех данных (пользователей/заданий)");
            System.out.println("3 - вывод данных по id (пользователя/задания)");
            System.out.println("4 - работа с пользователями (добавление/удаление/изменение(логин, пароль, роль))");
            System.out.println("5 - работа с задачами (добавление/удаление/изменение статуса)");
            System.out.println("6 - работа с категориями (добавление/удаление/изменение)");
            System.out.println("7 - завершить сессию пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                printUserInfo();
            } else if (inputUserItemMenu.equals("2")) {
                showPrintAllUserAndAllTaskInfoMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("3")) {
                showPrintUserAndTaskInfoByIdMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("4")) {
                showUserAddDelEditMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("5")) {
                showTaskAddDelEditMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("6")) {
                showCategoryAddDelEditMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("7")) {
                start();
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        } while (!inputUserItemMenu.equals("7"));
    }

    // меню для работы пользователя
    private void showUserMenu(int idUser) {
        do {
            System.out.println("\n1 - вывод данных текущего пользователя");
            System.out.println("2 - вывод всех задач пользователя");
            System.out.println("3 - работа с задачами");
            System.out.println("4 - изменить данные пользователя");
            System.out.println("5 - завершить сессию пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                printUserInfo();
            } else if (inputUserItemMenu.equals("2")) {
                taskServiceImpl.listUserTasks(userServiceImpl.getAuthorizedUserId());
            } else if (inputUserItemMenu.equals("3")) {
                showTaskUserMenuTitle();
                showTaskUserMenu(idUser);
            } else if (inputUserItemMenu.equals("4")) {
                showEditUserMenuTitle();
                showEditUserMenu(idUser);
            } else if (inputUserItemMenu.equals("5")) {
                start();
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        } while (!inputUserItemMenu.equals("5"));
    }

    // меню работы с задачами для пользоваетля
    private void showTaskUserMenu(int idUser) {
        while (true) {
            System.out.println("\n1 - вывод всех задач пользователя");
            System.out.println("2 - вывод информации о задаче по id задачи");
            System.out.println("3 - изменение статуса задачи");
            System.out.println("4 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                taskServiceImpl.listUserTasks(userServiceImpl.getAuthorizedUserId());
            } else if (inputUserItemMenu.equals("2")) {
                printTaskInfoFromIdTaskByUser();
            } else if (inputUserItemMenu.equals("3")) {
                editTaskStatusByIdToUser();
            } else if (inputUserItemMenu.equals("4")) {
                showUserMenuTitle();
                showUserMenu(idUser);
            }
        }
    }

    // меню редактирования данных пользователя для пользоваетля
    private void showEditUserMenu(int idUser) {
        System.out.println("\nКакие данные нужно изменить:");

        while (true) {
            System.out.println("\n1 - логин");
            System.out.println("2 - пароль");
            System.out.println("3 - все данные пользователя, кроме id и роли (для пользователя, самостоятельное изменение роли запрещено)");
            System.out.println("4 - выход в главное меню пользователя");

            System.out.println("\nВведите цифру нужного меню:");
            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                changeUserLogin();
            } else if (inputUserItemMenu.equals("2")) {
                changeUserPassword(idUser);
            } else if (inputUserItemMenu.equals("3")) {
                changeUserAllParametersForUser(idUser);
            } else if (inputUserItemMenu.equals("4")) {
                showUserMenuTitle();
                showUserMenu(idUser);
            } else {
                System.out.println("\nВведите номер меню из списка");
            }
        }
    }

    // меню редактирования данных пользователя для менеджера
    private void showEditUserForManagerMenu(int idTaskCreator) {
        System.out.println("\nКакие данные нужно изменить:");

        while (true) {
            System.out.println("\n1 - логин пользователя");
            System.out.println("2 - пароль пользователя");
            System.out.println("3 - роль пользователя");
            System.out.println("4 - все данные пользователя, кроме id");
            System.out.println("5 - выход в главное меню пользователя");

            System.out.println("\nВведите цифру нужного меню:");
            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                changeUserLoginForManager();
            } else if (inputUserItemMenu.equals("2")) {
                changeUserPasswordForManager();
            } else if (inputUserItemMenu.equals("3")) {
                changeUserRoleForManager();
            } else if (inputUserItemMenu.equals("4")) {
                changeUserAllParametersForManager();
            } else if (inputUserItemMenu.equals("5")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nВведите номер меню из списка");
            }
        }
    }

    // меню просмотра данных по всем пользователям и всем задачам
    private void showPrintAllUserAndAllTaskInfoMenu(int idTaskCreator) {
        while (true) {
            System.out.println("\n1 - вывод информации о всех пользователях");
            System.out.println("2 - вывод информации о всех заданиях");
            System.out.println("3 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                userServiceImpl.printUsersInfo();
            } else if (inputUserItemMenu.equals("2")) {
                taskServiceImpl.printTasksInfo();
            } else if (inputUserItemMenu.equals("3")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        }
    }

    // меню просмотра данных по пользователю и всем задаче по id
    private void showPrintUserAndTaskInfoByIdMenu(int idTaskCreator) {
        while (true) {
            System.out.println("\n1 - вывод информации о пользователе по id");
            System.out.println("2 - вывод информации о задаче по id");
            System.out.println("3 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                printUserInfoById();
            } else if (inputUserItemMenu.equals("2")) {
                printTaskInfoById(idTaskCreator);
            } else if (inputUserItemMenu.equals("3")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        }
    }

    // меню работы с пользователями для менеджера
    private void showUserAddDelEditMenu(int idTaskCreator) {
        while (true) {
            System.out.println("\n1 - добавление пользователя");
            System.out.println("2 - удаление пользователя");
            System.out.println("3 - изменение пользователя");
            System.out.println("4 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                showRegistrationMenuTitle();
                showRegistrationMenu();
            } else if (inputUserItemMenu.equals("2")) {
                deleteUserById();
            } else if (inputUserItemMenu.equals("3")) {
                showEditUserForManagerMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("4")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        }
    }

    // меню работы с задачами для менеджера
    public void showTaskAddDelEditMenu(int idTaskCreator) {
        while (true) {
            System.out.println("\n1 - добавление задачи");
            System.out.println("2 - удаление задачи");
            System.out.println("3 - изменение задачи");
            System.out.println("4 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                addTaskForManagerMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("2")) {
                deleteTaskForManagerMenu();
            } else if (inputUserItemMenu.equals("3")) {
                showEditTaskForManagerMenu(idTaskCreator);
            } else if (inputUserItemMenu.equals("4")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        }
    }

    // меню работы с категориями для менеджера
    private void showCategoryAddDelEditMenu(int idTaskCreator) {
        while (true) {
            System.out.println("\n1 - добавление категории");
            System.out.println("2 - удаление категории");
            System.out.println("3 - изменение категории");
            System.out.println("4 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                addTaskCategory();
            } else if (inputUserItemMenu.equals("2")) {
                deleteTaskCategory();
            } else if (inputUserItemMenu.equals("3")) {
                changeCategory();
            } else if (inputUserItemMenu.equals("4")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        }
    }

    // меню работы с одной задачей для менеджера
    private void showEditTaskForManagerMenu(int idTaskCreator) {
        while (true) {
            System.out.println("\n1 - изменить имя задачи");
            System.out.println("2 - изменить исполнителя задачи");
            System.out.println("3 - изменить статус задачи");
            System.out.println("4 - изменить категорию задачи");
            System.out.println("5 - изменить описание задачи");
            System.out.println("6 - выход в главное меню пользователя\n");
            System.out.println("Введите цифру нужного меню:");

            inputUserItemMenu = console.scanItemMenu();

            if (inputUserItemMenu.equals("1")) {
                changeTaskTopicForTask();
            } else if (inputUserItemMenu.equals("2")) {
                changeIdUserForTask();
            } else if (inputUserItemMenu.equals("3")) {
                changeTaskStatusForTask();
            } else if (inputUserItemMenu.equals("4")) {
                changeTaskCategoryForTask();
            } else if (inputUserItemMenu.equals("5")) {
                changeTaskDescriptionForTask();
            } else if (inputUserItemMenu.equals("6")) {
                showManagerMenu(idTaskCreator);
            } else {
                System.out.println("\nТакого меню не существует. Выбери только из указанных вариантов");
            }
        }
    }

    // приветственное сообщение меню данных пользователя
    private void showEditUserMenuTitle() {
        System.out.println("\nМеню изменения данных пользователя:");
    }

    // приветственное сообщение меню заботы с задачами
    private void showTaskUserMenuTitle() {
        System.out.println("\nМеню работы с задачами:");
    }

    // приветственное сообщение меню менеджера
    private void showManagerMenuTitle() {
        System.out.println("\nГлавное меню менеджера:");
        System.out.println("Для работы выбери нужное меню:");
    }

    // приветственное сообщение меню пользователя
    private void showUserMenuTitle() {
        System.out.println("\nГлавное меню пользователя:");
        System.out.println("Для работы выбери нужное меню:");
    }

    // меню полльзователя  для смены статуса задачи
    private void editTaskStatusByIdToUser() {
        String inputUserIdTaskForEditStatusString = "";
        int inputUserIdTaskForEditStatus = -1;
        String inputNewTaskStatusIdString = "";
        String taskInfoFromId = "";
        int inputNewIdTask = -1;

        System.out.println("\nИзменение статуса задачи");

        while (true) {
            System.out.println("\nВведите id задачи для смены статуса");
            inputUserIdTaskForEditStatusString = console.scanItemMenu();
            if (!console.isNumeric(inputUserIdTaskForEditStatusString)) {
                System.out.println("\nНеобходимо ввести число");
            } else if (taskServiceImpl.getIdTask(Integer.parseInt(inputUserIdTaskForEditStatusString)) == -1) {
                System.out.println("\nЗадачи с таким id не существует");
            } else {
                inputUserIdTaskForEditStatus = Integer.parseInt(inputUserIdTaskForEditStatusString);
                taskInfoFromId = taskServiceImpl.getTaskInfoByIdUserAndIdTask(userServiceImpl.getAuthorizedUserId(), inputUserIdTaskForEditStatus);
                System.out.println("\n" + taskInfoFromId);
                break;
            }
        }

        if (taskInfoFromId.contains("id")) {
            System.out.println("\nСписок статусов задачи:");
            taskServiceImpl.listTaskStatus();

            while (true) {
                System.out.println("\nВведите новый id статуса задачи");
                inputNewTaskStatusIdString = console.scanItemMenu();
                if (!console.isNumeric(inputNewTaskStatusIdString)) {
                    System.out.println("\nНеобходимо ввести число");
                } else if (taskServiceImpl.getTaskStatusId(Integer.parseInt(inputNewTaskStatusIdString)) == -1) {
                    System.out.println("\nВведите id нового статуса из списка");
                } else {
                    inputNewIdTask = Integer.parseInt(inputNewTaskStatusIdString);
                    int resultUpdate = taskServiceImpl.updateTaskStatus(inputUserIdTaskForEditStatus, inputNewIdTask);

                    if (resultUpdate == -1) {
                        System.out.println("\nОшибка, во время смены статуса задачи");
                    } else {
                        System.out.println("\nСтатус задачи успешно обновлен");
                    }
                    break;
                }
            }
        }
    }

    // меню полльзователя  для вывода на печать задачи по id задачи
    private void printTaskInfoFromIdTaskByUser() {
        int inputUserTaskId = -1;
        System.out.println("\nВведите id задачи:");
        inputUserTaskId = console.scanNumber();

        String taskInfoFromId = taskServiceImpl.getTaskInfoByIdUserAndIdTask(userServiceImpl.getAuthorizedUserId(), inputUserTaskId);
        System.out.println("\n" + taskInfoFromId);
    }

    // вывод информации о пользователе
    private void printUserInfo() {
        System.out.println("\nВывод информации о пользователе:");
        String userInfo = userServiceImpl.getUserInfo(userServiceImpl.getAuthorizedUserId());
        System.out.println(userInfo);
    }

    // вывод информации о пользователе по id пользователя
    private void printUserInfoById() {
        int tempInputIdUser = -1;
        int inputIdUser = -1;

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputIdUser = console.scanNumber();
            if (userServiceImpl.getIdUser(tempInputIdUser) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else {
                inputIdUser = tempInputIdUser;
                System.out.println("\nИнформация о пользователе с id - " + inputIdUser + ":");
                System.out.printf(userServiceImpl.getUserInfo(inputIdUser) + "\n");
                break;
            }
        }
    }

    // вывод информации о задаче по id задачи
    private void printTaskInfoById(int idTaskCreator) {
        int tempInputIdTask = -1;
        int inputIdTask = -1;

        while (true) {
            System.out.println("\nВведите id задачи");
            tempInputIdTask = console.scanNumber();

            if (taskServiceImpl.getIdTask(tempInputIdTask) == -1) {
                System.out.println("\nНет задачи с таким id");
                showPrintUserAndTaskInfoByIdMenu(idTaskCreator);
            } else {
                inputIdTask = tempInputIdTask;
                System.out.println("\nИнформация о задачи с id - " + inputIdTask + ":");
                System.out.printf(taskServiceImpl.getTaskInfo(inputIdTask) + "\n");
                break;
            }
        }
    }

    // смена логина пользователя
    private void changeUserLogin() {
        System.out.println("\nВведите новый логин:");
        String newUserLogin = console.scanLogin();

        if (userServiceImpl.getIdUser(newUserLogin) != -1) {
            System.out.println("\nЛогин занят");
        } else if (userServiceImpl.checkUpdateAuthorizedUserLogin(newUserLogin)) {
            System.out.println("\nЛогин пользователя успешно изменен");
        } else {
            System.out.println("\nНе удалось изменить логин пользователя");
        }
    }

    // смена логина менеджера
    private void changeUserLoginForManager() {
        int tempInputUserId = -1;
        int inputUserId = -1;
        String newLogin = "";

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputUserId = console.scanNumber();

            if (userServiceImpl.getIdUser(tempInputUserId) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else {
                inputUserId = tempInputUserId;
                break;
            }
        }

        System.out.println("\n" + userServiceImpl.getUserInfo(inputUserId));
        System.out.println("\nВведите желаемый логин:");
        newLogin = console.scanLogin();

        if (userServiceImpl.getIdUser(newLogin) != -1) {
            System.out.println("\nЛогин занят");
        } else if (userServiceImpl.checkUpdateUserLogin(inputUserId, newLogin)) {
            System.out.println("\nЛогин пользователя успешно изменен");
        } else {
            System.out.println("\nНе удалось изменить логин пользователя");
        }
    }

    // смена пароля для менеджера
    private void changeUserPasswordForManager() {
        int tempInputIdUser = -1;
        int inputUserId = -1;
        String newPassword = "";

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputIdUser = console.scanNumber();

            if (userServiceImpl.getIdUser(tempInputIdUser) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else {
                inputUserId = tempInputIdUser;
                break;
            }
        }

        System.out.println("\n" + userServiceImpl.getUserInfo(inputUserId));
        System.out.println("\nВведите желаемый пароль:");
        newPassword = console.scanPassword();

        if (userServiceImpl.checkUpdateUserPassword(inputUserId, newPassword)) {
            System.out.println("\nПароль пользователя успешно изменен");
        }
    }

    // смена пароля для пользователя
    private void changeUserPassword(int idUser) {
        System.out.println("\nВведите желаемый пароль:");
        String newPassword = console.scanPassword();

        if (userServiceImpl.checkUpdateUserPassword(idUser, newPassword)) {
            System.out.println("\nПароль пользователя успешно изменен");
        } else {
            System.out.println("Пароль пользователя изменить не удалось");
        }
    }

    //смена роли пользователя для менеджера
    private void changeUserRoleForManager() {
        int tempInputIdUser = -1;
        int inputIdUser = -1;
        int newIdUserRole = -1;

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputIdUser = console.scanNumber();

            if (userServiceImpl.getIdUser(tempInputIdUser) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else {
                inputIdUser = tempInputIdUser;
                break;
            }
        }

        if (userServiceImpl.getAuthorizedUserRole() == inputIdUser) {
            System.out.println("\nЗапрещено менять id роли текущему пользователю");
        } else {
            while (true) {
                System.out.println("\nВыберите id роли пользователя:");
                userRoleServiceImpl.printUserRole();
                System.out.println("\nВведите необходимый id роли");
                newIdUserRole = console.scanNumber();

                if (!userRoleServiceImpl.isValid(newIdUserRole)) {
                    System.out.println("\nВведенное id не существует");
                } else {
                    break;
                }
            }

            if (userServiceImpl.checkUpdateUserRole(inputIdUser, newIdUserRole)) {
                System.out.println("\nроль пользователя успешно изменена");
            } else {
                System.out.println("\nОшибка при изменении роли пользователя");
            }
        }
    }

    // смена всех параметров пользователя для менеджера
    private void changeUserAllParametersForManager() {
        int tempInputIdUser = 0;
        int inputIdUser = 0;
        String inputUserLogin = "";
        String inputUserPassword = "";
        int idUserRole = -1;
        String userPhoneNumber = "";
        String userEmail = "";
        String userFirstName = "";
        String userLastName = "";
        int userAge = 0;
        String roleIdString = "";
        boolean loginIsFree = false;
        boolean passwordIsValid = false;

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputIdUser = console.scanNumber();

            if (userServiceImpl.getIdUser(tempInputIdUser) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else {
                inputIdUser = tempInputIdUser;
                break;
            }
        }

        if (userServiceImpl.getAuthorizedUserRole() == inputIdUser) {
            System.out.println("\nЗапрещено менять id роли текущему пользователю");
        } else {
            while (true) {
                while (true) {
                    System.out.println("Введите новый логин пользователя:");

                    inputUserLogin = console.scanLogin();
                    if (userServiceImpl.checkUserLoginToDB(inputUserLogin)) {
                        System.out.println("\nВведенный логин пользователя уже занят!\n");
                    } else {
                        loginIsFree = true;
                        break;
                    }
                }

                while (loginIsFree) {
                    System.out.println("\nВведите новый пароль:");
                    inputUserPassword = console.scanPassword();
                    passwordIsValid = true;
                    break;
                }

                while (loginIsFree && passwordIsValid) {
                    System.out.println("\nВыберите новый id роли пользователя:");
                    userRoleServiceImpl.printUserRole();
                    System.out.println("\nВведите необходимый id роли");

                    roleIdString = console.scanItemMenu();
                    if (console.isNumeric(roleIdString)) {
                        idUserRole = Integer.parseInt(roleIdString);
                    } else {
                        System.out.println("\nВведенное id не существует");
                        continue;
                    }

                    if (!userRoleServiceImpl.isValid(idUserRole)) {
                        System.out.println("\nВведенное id не существует");
                    } else {
                        break;
                    }
                }
                userPhoneNumber = console.scanPhoneNumber();
                userEmail = console.scanEmail();
                System.out.println("Введите новое имя: ");
                userFirstName = console.scanFirstName();
                System.out.println("Введите новую фамилию: ");
                userLastName = console.scanLastName();
                System.out.println("Введите новый возраст: ");
                userAge = console.scanAge();

                if (userServiceImpl.updateUserToDB(inputIdUser, idUserRole, inputUserLogin, inputUserPassword, userPhoneNumber,
                        userEmail, userFirstName, userLastName, userAge)) {
                    System.out.println("\nПользователь успешно изменен");
                    break;
                } else {
                    System.out.println("\nОшибка во время изменения пользователя");
                }
            }
        }
    }

    // смена всех параметров пользователя для пользователя
    private void changeUserAllParametersForUser(int idUser) {
        int tempInputIdUser = 0;
        int inputIdUser = 0;
        String inputUserLogin = "";
        String inputUserPassword = "";
        String userPhoneNumber = "";
        String userEmail = "";
        String userFirstName = "";
        String userLastName = "";
        int userAge = 0;

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputIdUser = console.scanNumber();

            if (userServiceImpl.getIdUser(tempInputIdUser) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else {
                inputIdUser = tempInputIdUser;
                break;
            }
        }

        while (true) {
            while (true) {
                System.out.println("Введите новый логин пользователя:");

                inputUserLogin = console.scanLogin();
                if (userServiceImpl.checkUserLoginToDB(inputUserLogin)) {
                    System.out.println("\nВведенный логин пользователя уже занят!\n");
                } else {
                    break;
                }
            }

            System.out.println("\nВведите новый пароль:");
            inputUserPassword = console.scanPassword();
            userPhoneNumber = console.scanPhoneNumber();
            userEmail = console.scanEmail();
            System.out.println("Введите новое имя: ");
            userFirstName = console.scanFirstName();
            System.out.println("Введите новую фамилию: ");
            userLastName = console.scanLastName();
            System.out.println("Введите новый возраст: ");
            userAge = console.scanAge();

            if (userServiceImpl.updateUserToDB(inputIdUser, userRoleServiceImpl.getIdUserRoleByIdUser(idUser), inputUserLogin, inputUserPassword, userPhoneNumber,
                    userEmail, userFirstName, userLastName, userAge)) {
                System.out.println("\nПользователь успешно изменен");
                break;
            } else {
                System.out.println("\nОшибка во время изменения пользователя");
            }
        }
    }

    // Удаление пользователя по id
    private void deleteUserById() {
        System.out.println("\nУдаление пользователя по id");
        System.out.println("Внимание!!! При удалении пользователя удалятся и все его задачи");

        int tempInputIdUser = -1;
        int inputUserId = -1;

        while (true) {
            System.out.println("\nВведите id пользователя");
            tempInputIdUser = console.scanNumber();

            if (userServiceImpl.getIdUser(tempInputIdUser) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else if (userServiceImpl.getAuthorizedUserId() == tempInputIdUser) {
                System.out.println("\nНевозможно удалить самого себя!");
            } else {
                inputUserId = tempInputIdUser;

                if (userServiceImpl.deleteUser(inputUserId)) {
                    System.out.println("\nПользователь с id - " + inputUserId + " успешно удален");
                } else {
                    System.out.println("\nНе удалось удалить пользователя с id - " + inputUserId);
                }
                break;
            }
        }
    }

    // Добавление задачи менеджером
    private void addTaskForManagerMenu(int idTaskCreator) {
        System.out.println("\nДобавление задачи");

        String inputTaskTopicString = "";
        String inputIdUserForTaskString = "";
        int inputIdUserForTask = -1;
        String inputIdStatusForTaskString = "";
        int inputIdStatusForTask = -1;
        String inputIdTaskCategoryForTaskString = "";
        String inputTaskDescriptionString = "";
        int inputIdTaskCategoryForTask = -1;

        while (true) {
            System.out.println("\nВведите id пользователя, для которого добавляется задача");
            inputIdUserForTaskString = console.scanItemMenu();

            if (!console.isNumeric(inputIdUserForTaskString)) {
                System.out.println("\nВведите число");
            } else if (userServiceImpl.getIdUser(Integer.parseInt(inputIdUserForTaskString)) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else if (userRoleServiceImpl.getUserRole(Integer.parseInt(inputIdUserForTaskString)).toLowerCase().equals("manager")) {
                System.out.println("\n Для Manager нельзя добавлять задачу");
            } else {
                inputIdUserForTask = Integer.parseInt(inputIdUserForTaskString);
                break;
            }
        }

        System.out.println("\nВведите название задачи");
        inputTaskTopicString = console.scanStringForty();
        System.out.println("\nВведите описание задачи");
        inputTaskDescriptionString = console.scanStringTwoFiveFive();

        inputIdStatusForTaskString = "to do";                                                             //для всех задач начальное значение "to do", если нужно другое, то реализовать ввод id статуса
        inputIdStatusForTask = taskServiceImpl.getIdTaskStatusFromTaskStatus(inputIdStatusForTaskString);

        while (true) {
            System.out.println("\nВыберите id категории:");
            taskServiceImpl.printListCategory();
            System.out.println("\nВведите необходимый id категории или нажмите Enter (будет назначена категория default):");

            inputIdTaskCategoryForTaskString = console.scanItemMenu();

            if (inputIdTaskCategoryForTaskString.equals("")) {
                inputIdTaskCategoryForTask = taskServiceImpl.getIdTaskCategoryFromNameTaskCategory("default");
                break;
            } else if (!console.isNumeric(inputIdTaskCategoryForTaskString)) {
                System.out.println("\nВведите число");
            } else if (taskServiceImpl.getIdTaskCategory(Integer.parseInt(inputIdTaskCategoryForTaskString)) == -1) {
                System.out.println("\nНет категории с таким id");
            } else {
                inputIdTaskCategoryForTask = Integer.parseInt(inputIdTaskCategoryForTaskString);
                break;
            }
        }

        if (taskServiceImpl.addTaskToDB(idTaskCreator, inputIdUserForTask, inputIdTaskCategoryForTask, inputIdStatusForTask, inputTaskTopicString, Timestamp.valueOf(LocalDateTime.now()), inputTaskDescriptionString)) {
            System.out.println("\nЗадача успешно создана для пользователя с id - " + inputIdUserForTask);
        } else {
            System.out.println("\nОшибка при добавлении задания");
        }
    }

    // Удаление задачи для менеджера
    private void deleteTaskForManagerMenu() {
        System.out.println("\nУдаление задачи");

        int tempIdTask = -1;
        int inputIdTask = -1;

        while (true) {
            System.out.println("\nВведите id задачи для удаления");
            tempIdTask = console.scanNumber();

            if (taskServiceImpl.getIdTask(tempIdTask) == -1) {
                System.out.println("\nНет задачи с таким id");
            } else {
                inputIdTask = tempIdTask;
                break;
            }
        }

        if (taskServiceImpl.deleteTaskByIdTask(inputIdTask)) {
            System.out.println("\nЗадача c ID - " + inputIdTask + " успешно удалена");
        } else {
            System.out.println("\nОшибка при удалении задания с id - " + inputIdTask);
        }
    }

    // Изменение название задачи
    private void changeTaskTopicForTask() {
        System.out.println("\nИзменение название задачи");

        int inputIdTask = -1;
        String inputNewTaskName = "";
        int IdTask = -1;

        while (true) {
            System.out.println("\nВведите id задачи, для которого нужно изменить имя");
            inputIdTask = console.scanNumber();
            if (taskServiceImpl.getIdTask(inputIdTask) == -1) {
                System.out.println("\nНет задачи с таким id");
            } else {
                IdTask = inputIdTask;
                break;
            }
        }

        System.out.println("\nВведите новое имя задачи:");
        inputNewTaskName = console.scanStringForty();

        if (taskServiceImpl.updateTaskTopic(IdTask, inputNewTaskName)) {
            System.out.println("\nИзменение название для задачи с id - " + IdTask + " прошло успешно");
        } else {
            System.out.println("\nОшибка изменения названия для задачи с id - " + IdTask);
        }
    }

    // измененение описания задачи
    private void changeTaskDescriptionForTask() {
        System.out.println("\nИзменение описания задания");

        int inputIdTask = -1;
        String inputNewTaskName = "";
        int IdTask = -1;

        while (true) {
            System.out.println("\nВведите id задачи, для которого нужно изменить имя");
            inputIdTask = console.scanNumber();
            if (taskServiceImpl.getIdTask(inputIdTask) == -1) {
                System.out.println("\nНет задачи с таким id");
            } else {
                IdTask = inputIdTask;
                break;
            }
        }

        System.out.println("\nВведите новое описание задачи:");
        inputNewTaskName = console.scanStringTwoFiveFive();

        if (taskServiceImpl.updateTaskDescription(IdTask, inputNewTaskName)) {
            System.out.println("\nИзменение описания для задачи с id - " + IdTask + " прошло успешно");
        } else {
            System.out.println("\nОшибка изменения названия для задачи с id - " + IdTask);
        }
    }

    // изменение исполнителя задания
    private void changeIdUserForTask() {
        System.out.println("\nИзменение исполнителя задачи");

        String inputIdTaskString = "";
        String inputIdUserString = "";
        int inputTaskId = -1;
        int inputUserId = -1;

        while (true) {
            System.out.println("\nВведите id задачи, для которой нужно изменить исполнителя");
            inputIdTaskString = console.scanItemMenu();

            if (!console.isNumeric(inputIdTaskString)) {
                System.out.println("\nВведите число");
            } else if (taskServiceImpl.getIdTask(Integer.parseInt(inputIdTaskString)) == -1) {
                System.out.println("\nНет задачи с таким id");
            } else {
                inputTaskId = Integer.parseInt(inputIdTaskString);
                break;
            }
        }

        while (true) {
            System.out.println("\nВведите id пользователя, которому нужно добавить задачу");
            inputIdUserString = console.scanItemMenu();

            if (!console.isNumeric(inputIdUserString)) {
                System.out.println("\nВведите число");
            } else if (userServiceImpl.getIdUser(Integer.parseInt(inputIdUserString)) == -1) {
                System.out.println("\nНет пользователя с таким id");
            } else if (userRoleServiceImpl.getUserRole(Integer.parseInt(inputIdUserString)).toLowerCase().equals("manager")) {
                System.out.println("\nДля Manager нельзя добавлять задания");
            } else {
                inputUserId = Integer.parseInt(inputIdUserString);
                break;
            }
        }

        if (taskServiceImpl.updateUserIdForTask(inputTaskId, inputUserId)) {
            System.out.println("\nИзменение владельца для задачи с id - " + inputTaskId + " прошло успешно");
        } else {
            System.out.println("\nОшибка изменения названия для задания с id - " + inputTaskId);
        }
    }

    // Изменение статуса задания
    private void changeTaskStatusForTask() {
        int inputIdTaskForEditStatus = -1;
        int inputNewIdTaskStatus = -1;
        String taskInfoFromId = "";

        System.out.println("\nИзменением статуса задачи");

        while (true) {
            System.out.println("\nВведите id задачи для смены статуса");
            inputIdTaskForEditStatus = console.scanNumber();
            if (taskServiceImpl.getIdTask(inputIdTaskForEditStatus) == -1) {
                System.out.println("\nЗадача с таким id не существует");
            } else {
                taskInfoFromId = taskServiceImpl.getTaskInfo(inputIdTaskForEditStatus);
                System.out.println("\n" + taskInfoFromId);
                break;
            }
        }

        if (taskInfoFromId.contains("id")) {
            System.out.println("\nСписок статусов задачи:");
            taskServiceImpl.listTaskStatus();

            while (true) {
                System.out.println("\nВведите новый id статуса задачи");
                inputNewIdTaskStatus = console.scanNumber();
                if (taskServiceImpl.getTaskStatusId(inputNewIdTaskStatus) == -1) {
                    System.out.println("\nВведите id нового статуса из списка");
                } else {
                    int resultUpdate = taskServiceImpl.updateTaskStatus(inputIdTaskForEditStatus, inputNewIdTaskStatus);

                    if (resultUpdate == -1) {
                        System.out.println("\nОшибка, во время смены статуса задачи");
                    } else {
                        System.out.println("\nСтатус задачи успешно обновлен");
                    }
                    break;
                }
            }
        }
    }

    // Изменение категории задачи
    private void changeTaskCategoryForTask() {
        System.out.println("\nИзменением категории для задачи");

        int inputIdTask = -1;
        int inputIdTaskCategory = -1;
        String taskInfoFromId = "";
        while (true) {
            System.out.println("\nВведите id задачи для смены статуса");
            inputIdTask = console.scanNumber();
            if (taskServiceImpl.getIdTask(inputIdTask) == -1) {
                System.out.println("\nЗадача с таким id не существует");
            } else {
                taskInfoFromId = taskServiceImpl.getTaskInfo(inputIdTask);
                System.out.println("\n" + taskInfoFromId);
                break;
            }
        }

        if (taskInfoFromId.contains("id")) {
            System.out.println("\nСписок поддерживаемых категорий задач:");
            taskServiceImpl.printListCategory();
            while (true) {
                System.out.println("\nВведите id категории задач, на которую нужно сменить");
                inputIdTaskCategory = console.scanNumber();
                if (taskServiceImpl.getIdTaskCategory(inputIdTaskCategory) == -1) {
                    System.out.println("\nНет категории с таким id");
                } else {
                    break;
                }
            }
        }
        if (taskServiceImpl.updateIdTaskCategoryFromIdTask(inputIdTask, inputIdTaskCategory)) {
            System.out.println("\nИзменение категории для задачи с id - " + inputIdTask + " прошло успешно");
        } else {
            System.out.println("\nОшибка изменения категории для задачи с id - " + inputIdTask);
        }
    }

    // добавление категории задача
    private void addTaskCategory() {
        System.out.println("\nДобавление категории задач");
        String inputNewNameTaskCategoryString = "";
        System.out.println("\nСписок категорий задач:");
        taskServiceImpl.printListCategory();
        while (true) {
            System.out.println("\nВведите название новой категории задач");
            inputNewNameTaskCategoryString = console.scanStringForty();

            if (taskServiceImpl.getIdTaskCategoryFromNameTaskCategory(inputNewNameTaskCategoryString) != -1) {
                System.out.println("\nТакая категория задач уже существует");
            } else {
                break;
            }
        }
        if (taskServiceImpl.addCategory(inputNewNameTaskCategoryString)) {
            System.out.println("\nКатегория \"" + inputNewNameTaskCategoryString + "\" успешно создана");
        } else {
            System.out.println("\nОшибка при создании категории \"" + inputNewNameTaskCategoryString + "\"");
        }
    }

    // удаление категории задача
    private void deleteTaskCategory() {
        System.out.println("\nУдаление категории задач");

        String inputIdTaskCategoryString = "";
        int inputIdTaskCategory = -1;
        System.out.println("\nСписок категорий задач:");
        taskServiceImpl.printListCategory();
        while (true) {
            System.out.println("\nВведите id категории задач для удаления");
            inputIdTaskCategoryString = console.scanItemMenu();

            if (!console.isNumeric(inputIdTaskCategoryString)) {
                System.out.println("\nВведите число");
            } else if (taskServiceImpl.getIdTaskCategory(Integer.parseInt(inputIdTaskCategoryString)) == -1) {
                System.out.println("\nНет категории с таким id");
            } else if (taskServiceImpl.getNameTaskCategoryFromIdTaskCategory(Integer.parseInt(inputIdTaskCategoryString)).equals("default")) {
                System.out.println("\nНевозможно удалить категорию по умолчанию");
            } else {
                inputIdTaskCategory = Integer.parseInt(inputIdTaskCategoryString);
                break;
            }
        }

        int defaultCategoryId = taskServiceImpl.getIdTaskCategoryFromNameTaskCategory("default");

        if (taskServiceImpl.deleteCategory(inputIdTaskCategory, defaultCategoryId)) {
            System.out.println("\nКатегория с id - " + inputIdTaskCategory + " успешно удалена");
        } else {
            System.out.println("\nОшибка при удалении категории с id - " + inputIdTaskCategory);
        }
    }


    private void changeCategory() {
        System.out.println("\nИзменение категории задачи");

        String inputIdTaskCategoryString = "";
        String inputNewTaskCategoryName = "";
        int inputIdTaskCategory = -1;
        System.out.println("\nСписок категорий задач:");
        taskServiceImpl.printListCategory();
        while (true) {
            System.out.println("\nВведите id категории задачи для изменения");
            inputIdTaskCategoryString = console.scanItemMenu();

            if (!console.isNumeric(inputIdTaskCategoryString)) {
                System.out.println("\nВведите число");
            } else if (taskServiceImpl.getIdTaskCategory(Integer.parseInt(inputIdTaskCategoryString)) == -1) {
                System.out.println("\nНет категории с таким id");
            } else if (taskServiceImpl.getNameTaskCategoryFromIdTaskCategory(Integer.parseInt(inputIdTaskCategoryString)).equals("default")) {
                System.out.println("\nНевозможно изменить категорию по умолчанию");
            } else {
                inputIdTaskCategory = Integer.parseInt(inputIdTaskCategoryString);
                break;
            }
        }

        System.out.println("\nВведите новое название категории");
        inputNewTaskCategoryName = console.scanItemMenu();

        if (taskServiceImpl.updateNameTaskCategory(inputIdTaskCategory, inputNewTaskCategoryName)) {
            System.out.println("\nНазвание категории успешно изменено");
        } else {
            System.out.println("\nОшибка во время изменения названия категории");
        }
    }
}
