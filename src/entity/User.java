package entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class User {
    private int idUser;
    private int idUserRole;
    private String userLogin;
    private String userPassword;
    private String userPhoneNumber;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private int userAge;
    private Timestamp userDateUpdate;

    public User(int idUserRole, String userLogin, String userPassword, String userPhoneNumber,
                String userEmail, String userFirstName, String userLastName, int userAge, Timestamp userDateUpdate) {
        this.idUserRole = idUserRole;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userAge = userAge;
        this.userDateUpdate = userDateUpdate;
    }

    public User(int idUser, int idUserRole, String userLogin, String userPassword,
                String userPhoneNumber, String userEmail, String userFirstName,
                String userLastName, int userAge, Timestamp userDateUpdate) {
        this.idUser = idUser;
        this.idUserRole = idUserRole;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userAge = userAge;
        this.userDateUpdate = userDateUpdate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(int idUserRole) {
        this.idUserRole = idUserRole;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public Timestamp getUserDateUpdate() {
        return userDateUpdate;
    }

    public void setUserDateUpdate(Date userDateUpdate) {
        this.userDateUpdate = Timestamp.valueOf(LocalDateTime.now());
    }

}
