package ua.edu.ukma.domain;

import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.util.ValidationUtils;

import java.util.Objects;

public class Faculty implements Identifiable<Integer>{
    private static int counter = 1;

    private final int id;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty(String name, String shortName) {
        ValidationUtils.validateNotEmpty(name, "Faculty name");
        ValidationUtils.validateNotEmpty(shortName, "Faculty short name");
        ValidationUtils.validateNoDigits(name, "Faculty name");
        ValidationUtils.validateNoDigits(shortName, "Faculty short name");
        this.id = counter++;
        this.name = name;
        this.shortName = shortName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateNotEmpty(name, "Faculty name");
        ValidationUtils.validateNoDigits(name, "Faculty name");
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        ValidationUtils.validateNotEmpty(shortName, "Faculty short name");
        ValidationUtils.validateNoDigits(shortName, "Faculty short name");
        this.shortName = shortName;
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        if (dean == null) throw new IllegalArgumentException("Dean cannot be null");
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
