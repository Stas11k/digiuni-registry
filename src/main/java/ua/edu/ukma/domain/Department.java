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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Department code cannot be empty");
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

}
