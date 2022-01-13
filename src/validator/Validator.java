package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    // проверка логина по шаблону
    public boolean validateLogin(String inputString) {
        Pattern pattern = Pattern.compile("^(?!!\\\\@\\\\#\\\\$\\\\%\\\\^\\\\&\\\\.\\\\*\\\\(\\\\)_\\\\+\\\\-\\\\=\\\\/\\\\.\\\\,\\\\ \\\\[ \\\\]\\{\\ \\\\}$)([A-Za-z0-9]{2,})");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    // проверка имени по шаблону
    public boolean validateFirstName(String inputString) {
        Pattern pattern = Pattern.compile("^(?!!\\\\@\\\\#\\\\$\\\\%\\\\^\\\\&\\\\.\\\\*\\\\(\\\\)_\\\\+\\\\-\\\\=\\\\/\\\\.\\\\,\\\\ \\\\[ \\\\]\\{\\ \\\\}$)([A-Za-zА-Яа-я]{2,})");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    // проверка фамилии по шаблону
    public boolean validateLastName(String inputString) {
        Pattern pattern = Pattern.compile("^(?!!\\\\@\\\\#\\\\$\\\\%\\\\^\\\\&\\\\.\\\\*\\\\(\\\\)_\\\\+\\\\-\\\\=\\\\/\\\\.\\\\,\\\\ \\\\[ \\\\]\\{\\ \\\\}$)([A-Za-zА-Яа-я]{2,})");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    // проверка пароля по шаблону
    public boolean validatePassword(String inputString) {
        Pattern pattern = Pattern.compile("^(?!!\\\\@\\\\#\\\\$\\\\%\\\\^\\\\&\\\\.\\\\*\\\\(\\\\)_\\\\+\\\\-\\\\=\\\\/\\\\.\\\\,\\\\ \\\\[ \\\\]\\{\\ \\\\}$)([A-Za-z0-9]{2,})");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    // проверка номера телефона по шаблону
    public boolean validatePhoneNumber(String inputString) {
        Pattern pattern = Pattern.compile("^[+]\\d{3}[(]\\d{2}[)]\\d{3}[-]\\d{2}[-]\\d{2}");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    // проверка электронной почты по шаблону
    public boolean validateEmail(String inputString) {
        Pattern pattern = Pattern.compile("^\\S+[@]\\S+[.]\\S+");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
