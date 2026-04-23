package ua.edu.ukma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.io.AsyncSaveService;
import ua.edu.ukma.io.DataBootstrap;
import ua.edu.ukma.io.DataContext;
import ua.edu.ukma.repository.InMemoryRepository;
import ua.edu.ukma.repository.Repository;
import ua.edu.ukma.service.*;
import ua.edu.ukma.ui.ConsoleMenu;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started in console mode");

        Repository<Faculty, Integer> facultyRepo = new InMemoryRepository<>();
        Repository<Department, Integer> departmentRepo = new InMemoryRepository<>();
        Repository<Specialty, Integer> specialtyRepo = new InMemoryRepository<>();
        Repository<Student, Integer> studentRepo = new InMemoryRepository<>();
        Repository<Teacher, Integer> teacherRepo = new InMemoryRepository<>();

        DataBootstrap bootstrap = new DataBootstrap();
        University university = bootstrap.loadAll(facultyRepo, departmentRepo, specialtyRepo, teacherRepo, studentRepo);

        AsyncSaveService saveService = new AsyncSaveService();
        DataContext dataContext = new DataContext(facultyRepo, departmentRepo, specialtyRepo, teacherRepo, studentRepo, university);

        FacultyService facultyService = new FacultyService(facultyRepo, saveService, dataContext);
        DepartmentService departmentService = new DepartmentService(departmentRepo, saveService, dataContext);
        SpecialtyService specialtyService = new SpecialtyService(specialtyRepo, saveService, dataContext);
        StudentService studentService = new StudentService(studentRepo, saveService, dataContext);
        TeacherService teacherService = new TeacherService(teacherRepo, saveService, dataContext);

        ConsoleMenu menu = new ConsoleMenu(facultyService, departmentService, specialtyService, studentService, teacherService, university);
        menu.start();
        logger.info("Application finished");
    }
}