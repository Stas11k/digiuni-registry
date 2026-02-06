package ua.edu.ukma.domain;

public class Department {
    private String code;
    private String name;
    private Faculty faculty;
    private Teacher head;
    private String location;

    public Department(String code, String name, Faculty faculty) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
    }
}
