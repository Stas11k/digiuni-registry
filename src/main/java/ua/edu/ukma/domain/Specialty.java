package ua.edu.ukma.domain;

public class Specialty {
    private static int counter = 1;

    private final int id;
    private String name;
    private Department department;

    public Specialty(String name, Department department) {
        this.id = counter++;
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
