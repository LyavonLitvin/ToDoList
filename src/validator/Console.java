package validator;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Console {
    Validator validator = new Validator();

    // ввод с клавиатуры числа
    public int scanNumber() {
        int number = 0;
        boolean userInputCorrect = false;
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                number = scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Вы ввели не число, повторите ввод");
                continue;
            }
            userInputCorrect = true;
        } while (!userInputCorrect);
        return number;
    }

    // ввод с клавиатуры пункта меню
    public String scanItemMenu() {
        String controlString;
        Scanner scanner = new Scanner(System.in);
        controlString = scanner.nextLine();
        return controlString;
    }

    // ввод с клавиатуры строки от 2 до 20 символов
    public String scanStringTwenty() {
        boolean userInputCorrect = false;
        String controlString;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Длина строки от 2 до 20 символов");
            controlString = scanner.nextLine();
            if (controlString.length() <= 20 && controlString.length() >= 2) {
                userInputCorrect = true;
            } else {
                System.out.println("Длина строки больше 20 или короче 2 символов. Попробуйте снова");
            }
        } while (!userInputCorrect);
        return controlString;
    }

    // ввод с клавиатуры строки от 2 до 40 символов
    public String scanStringForty() {
        boolean userInputCorrect = false;
        String controlString;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Длина строки от 2 до 40 символов");
            controlString = scanner.nextLine();
            if (controlString.length() <= 40 && controlString.length() >= 2) {
                userInputCorrect = true;
            } else {
                System.out.println("Длина строки больше 40 или короче 2 символов. Попробуйте снова");
            }
        } while (!userInputCorrect);
        return controlString;
    }

    // ввод с клавиатуры строки от 2 до 255 символов
    public String scanStringTwoFiveFive() {
        boolean userInputCorrect = false;
        String controlString;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Длина строки до 255 символов");
            controlString = scanner.nextLine();
            if (controlString.length() <= 255 && controlString.length() >= 2) {
                userInputCorrect = true;
            } else {
                System.out.println("Длина строки больше 255 или короче 2 символов. Попробуйте снова");
            }
        } while (!userInputCorrect);
        return controlString;
    }

    // закрытие сканера
    public void closeScanner() {
        Scanner scanner = new Scanner(System.in);
        scanner.close();
    }

    // ввод с клавиатуры имени пользователя
    public String scanFirstName() {
        String scanningFirstName;
        do {
            System.out.println("В логине должны присутствовать только буквы");
            scanningFirstName = scanStringTwenty();
        } while (!validator.validateFirstName(scanningFirstName));
        return scanningFirstName;
    }

    // ввод с клавиатуры фамилии
    public String scanLastName() {
        String scanningLastName;
        do {
            System.out.println("В логине должны присутствовать только буквы");
            scanningLastName = scanStringTwenty();
        } while (!validator.validateLastName(scanningLastName));
        return scanningLastName;
    }

    // ввод с клавиатуры логина
    public String scanLogin() {
        String scanningLogin;
        do {
            System.out.println("В логине должны присутствовать латинские буквы и цыфры");
            scanningLogin = scanStringTwenty();
        } while (!validator.validateLogin(scanningLogin));
        return scanningLogin;
    }

    // ввод с клавиатуры пароля
    public String scanPassword() {
        String scanningPassword;
        do {
            System.out.println("В пароле должны присутствовать латинские буквы и цыфры");
            scanningPassword = scanStringTwenty();
        } while (!validator.validatePassword(scanningPassword));
        return scanningPassword;
    }

    // ввод с клавиатуры номера телефона
    public String scanPhoneNumber() {
        String scanningPhoneNumber;
        do {
            System.out.println("Введите телефонный номер в формате: +___(__)___-__-__");
            scanningPhoneNumber = scanStringForty();
        } while (!validator.validatePhoneNumber(scanningPhoneNumber));
        return scanningPhoneNumber;
    }

    // ввод с клавиатуры элекронной почты
    public String scanEmail() {
        String scanningEmail;
        do {
            System.out.println("Введите адресс электронной почты в формате: ________@_______.___");
            scanningEmail = scanStringForty();
        } while (!validator.validateEmail(scanningEmail));
        return scanningEmail;
    }

    // ввод с клавиатуры возраста с валидацией
    public int scanAge() {
        int scanningAge = 0;
        boolean userInputCorrect = false;
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Введите возраст: ___");
                scanningAge = scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Вы ввели не число, повторите ввод");
                continue;
            }
            if (scanningAge < 130 && scanningAge > 0) {
                userInputCorrect = true;
            } else {
                System.out.println("Введенный возраст за рамками разумного. Пожалуйста, повторите ввод возраста!");
            }
        } while (!userInputCorrect);
        return scanningAge;
    }

    // валидация строки на число
    public boolean isNumeric(String inputData) {
        try {
            Integer.parseInt(inputData);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
