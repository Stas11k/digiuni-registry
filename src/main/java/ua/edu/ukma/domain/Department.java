package ua.edu.ukma.domain;

import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.util.ValidationUtils;

import java.util.Objects;

public class Department implements Identifiable<Integer> {
    private static int counter = 1;

    private final int id;
    private String name;
    private Faculty faculty;
    private Teacher head;
    private String location;

    public Department(String name, Faculty faculty) {
        ValidationUtils.validateNotEmpty(name, "Department name");
        ValidationUtils.validateNoDigits(name, "Department name");
        if (faculty == null) throw new IllegalArgumentException("Faculty cannot be null");
        this.id = counter++;
        this.name = name;
        this.faculty = faculty;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateNotEmpty(name, "Department name");
        ValidationUtils.validateNoDigits(name, "Department name");
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        if (faculty == null) throw new IllegalArgumentException("Faculty cannot be null");
        this.faculty = faculty;
    }

    public Teacher getHead() {
        return head;
    }

    public void setHead(Teacher head) {
        this.head = head;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
