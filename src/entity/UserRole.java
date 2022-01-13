package entity;

public class UserRole {
    private int idUserRole;
    private String userRole;

    public UserRole() {
    }

    public UserRole(int idUserRole, String userRole) {
        this.idUserRole = idUserRole;
        this.userRole = userRole;
    }

    public int getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(int idUserRole) {
        this.idUserRole = idUserRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
