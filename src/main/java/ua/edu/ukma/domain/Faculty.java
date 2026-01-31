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
}
