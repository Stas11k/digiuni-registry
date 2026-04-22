package ua.edu.ukma.domain;

import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NoDigitsField;
import ua.edu.ukma.validation.NotBlankField;

import java.util.Objects;

public class Faculty implements Identifiable<Integer> {
    private static int counter = 1;

    private final int id;

    @NotBlankField(message = "Faculty name cannot be empty")
    @NoDigitsField(message = "Faculty name cannot contain digits")
    private String name;

    @NotBlankField(message = "Faculty short name cannot be empty")
    @NoDigitsField(message = "Faculty short name cannot contain digits")
    private String shortName;

    private Teacher dean;
    private String contacts;

    public Faculty(String name, String shortName) {
        this.id = counter++;
        this.name = name;
        this.shortName = shortName;
        AnnotationValidator.validate(this);
    }

    public Faculty(int id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
        AnnotationValidator.validate(this);
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        this.dean = dean;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
