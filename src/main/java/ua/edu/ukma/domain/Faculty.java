package ua.edu.ukma.domain;

public class Faculty {
    private static int counter = 1;

    private final int id;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    public Faculty(String name, String shortName) {
        this.id = counter++;
        this.name = name;
        this.shortName = shortName;
    }

    public Faculty(String name, String shortName, Teacher dean) {
        this.id = counter++;
        this.name = name;
        this.shortName = shortName;
        this.dean = dean;
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
