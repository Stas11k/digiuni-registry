package ua.edu.ukma.domain;

import ua.edu.ukma.util.ValidationUtils;

public class Department {
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

    public int getId() {
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
}
