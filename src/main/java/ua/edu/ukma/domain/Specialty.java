package ua.edu.ukma.domain;

import ua.edu.ukma.util.ValidationUtils;

public class Specialty {
    private static int counter = 1;

    private final int id;
    private String name;
    private Department department;

    public Specialty(String name, Department department) {
        ValidationUtils.validateNotEmpty(name, "Specialty name");
        ValidationUtils.validateNoDigits(name, "Specialty name");
        if (department == null) throw new IllegalArgumentException("Department cannot be null");
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
        ValidationUtils.validateNotEmpty(name, "Specialty name");
        ValidationUtils.validateNoDigits(name, "Specialty name");
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return name;
    }

}
