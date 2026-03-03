package ua.edu.ukma.domain;

import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.util.ValidationUtils;

import java.util.Objects;

public class Specialty implements Identifiable<Integer> {
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

    @Override
    public Integer getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty that = (Specialty) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
