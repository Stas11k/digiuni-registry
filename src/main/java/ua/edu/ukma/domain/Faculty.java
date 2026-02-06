package ua.edu.ukma.domain;

public class Faculty {
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;

    }

    public Faculty(String code, String name, String shortName, Teacher dean) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.dean = dean;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Faculty code cannot be empty");
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
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

}
