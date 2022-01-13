package entity;

public class TaskCategory {
    private int idTaskCategory;
    private String nameTaskCategory;

    public TaskCategory() {
    }

    public TaskCategory(int idTaskCategory, String nameTaskCategory) {
        this.idTaskCategory = idTaskCategory;
        this.nameTaskCategory = nameTaskCategory;
    }

    public int getIdTaskCategory() {
        return idTaskCategory;
    }

    public void setIdTaskCategory(int idTaskCategory) {
        this.idTaskCategory = idTaskCategory;
    }

    public String getNameTaskCategory() {
        return nameTaskCategory;
    }

    public void setNameTaskCategory(String nameTaskCategory) {
        this.nameTaskCategory = nameTaskCategory;
    }
}
