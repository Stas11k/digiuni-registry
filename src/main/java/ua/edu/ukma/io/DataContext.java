package ua.edu.ukma.io;

import ua.edu.ukma.domain.*;
import ua.edu.ukma.repository.Repository;

public class DataContext {
    private final Repository<Faculty, Integer> facultyRepo;
    private final Repository<Department, Integer> departmentRepo;
    private final Repository<Specialty, Integer> specialtyRepo;
    private final Repository<Teacher, Integer> teacherRepo;
    private final Repository<Student, Integer> studentRepo;
    private final University university;

    public DataContext(Repository<Faculty, Integer> facultyRepo, Repository<Department, Integer> departmentRepo, Repository<Specialty, Integer> specialtyRepo, Repository<Teacher, Integer> teacherRepo, Repository<Student, Integer> studentRepo, University university) {
        this.facultyRepo = facultyRepo;
        this.departmentRepo = departmentRepo;
        this.specialtyRepo = specialtyRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.university = university;
    }

    public Repository<Faculty, Integer> facultyRepo() {
        return facultyRepo;
    }

    public Repository<Department, Integer> departmentRepo() {
        return departmentRepo;
    }

    public Repository<Specialty, Integer> specialtyRepo() {
        return specialtyRepo;
    }

    public Repository<Teacher, Integer> teacherRepo() {
        return teacherRepo;
    }

    public Repository<Student, Integer> studentRepo() {
        return studentRepo;
    }

    public University university() {
        return university;
    }
}