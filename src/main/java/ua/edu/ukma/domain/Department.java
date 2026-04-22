package ua.edu.ukma.domain;

import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NoDigitsField;
import ua.edu.ukma.validation.NotBlankField;

import java.util.Objects;

public class Department implements Identifiable<Integer> {
    private static int counter = 1;

    private final int id;

    @NotBlankField(message = "Department name cannot be empty")
    @NoDigitsField(message = "Department name cannot contain digits")
    private String name;

    private Faculty faculty;
    private Teacher head;
    private String location;

    public Department(String name, Faculty faculty) {
        if (faculty == null) throw new ValidationException("Faculty cannot be null");
        this.id = counter++;
        this.name = name;
        this.faculty = faculty;
        AnnotationValidator.validate(this);
    }

    public Department(int id, String name, Faculty faculty) {
        if (faculty == null) throw new ValidationException("Faculty cannot be null");
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        if (id >= counter) counter = id + 1;
        AnnotationValidator.validate(this);
    }

    public static void resetCounter() {
        counter = 1;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        AnnotationValidator.validate(this);
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        if (faculty == null) throw new ValidationException("Faculty cannot be null");
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
