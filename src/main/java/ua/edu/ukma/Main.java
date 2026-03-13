package ua.edu.ukma;

import ua.edu.ukma.domain.*;
import ua.edu.ukma.repository.InMemoryRepository;
import ua.edu.ukma.repository.Repository;
import ua.edu.ukma.service.*;
import ua.edu.ukma.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {

        Repository<Faculty, Integer> facultyRepo = new InMemoryRepository<>();
        Repository<Department, Integer> departmentRepo = new InMemoryRepository<>();
        Repository<Specialty, Integer> specialtyRepo = new InMemoryRepository<>();
        Repository<Student, Integer> studentRepo = new InMemoryRepository<>();
        Repository<Teacher, Integer> teacherRepo = new InMemoryRepository<>();

        FacultyService facultyService = new FacultyService(facultyRepo);
        DepartmentService departmentService = new DepartmentService(departmentRepo);
        SpecialtyService specialtyService = new SpecialtyService(specialtyRepo);
        StudentService studentService = new StudentService(studentRepo);
        TeacherService teacherService = new TeacherService(teacherRepo);

        Faculty f1 = new Faculty("Faculty of Informatics", "FI");
        Faculty f2 = new Faculty("Faculty of Humanities", "FH");
        facultyService.add(f1);
        facultyService.add(f2);

        Department d1 = new Department("Software Engineering", f1);
        Department d2 = new Department("Computer Science", f1);
        Department d3 = new Department("History", f2);
        departmentService.add(d1);
        departmentService.add(d2);
        departmentService.add(d3);

        Specialty s1 = new Specialty("Software Engineering", d1);
        Specialty s2 = new Specialty("Computer Science", d2);
        Specialty s3 = new Specialty("History", d3);
        specialtyService.add(s1);
        specialtyService.add(s2);
        specialtyService.add(s3);

        Teacher t1 = new Teacher("Petrenko", "Ivan", "Olehovych", "Professor", d1);
        Teacher t2 = new Teacher("Shevchenko", "Olena", "Mykolaivna", "Associate Professor", d2);
        Teacher t3 = new Teacher("Bondar", "Mariia", "Ivanivna", "Senior Lecturer", d3);
        teacherService.add(t1);
        teacherService.add(t2);
        teacherService.add(t3);

        Student st1 = new Student("Koval", "Andrii", "Petrovych", "SE-001", 2, 1, s1);
        Student st2 = new Student("Melnyk", "Iryna", "Stepanivna", "CS-001", 3, 2, s2);
        Student st3 = new Student("Tkachenko", "Sofiia", "Olehivna", "HI-001", 1, 1, s3);
        Student st4 = new Student("Kravets", "Oleh", "Serhiiovych", "SE-002", 2, 3, s1);
        studentService.add(st1);
        studentService.add(st2);
        studentService.add(st3);
        studentService.add(st4);

        ConsoleMenu menu = new ConsoleMenu(facultyService, departmentService, specialtyService, studentService, teacherService);
        menu.start();
    }
}