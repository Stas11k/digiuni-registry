package ua.edu.ukma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.io.AsyncSaveService;
import ua.edu.ukma.io.DataBootstrap;
import ua.edu.ukma.io.DataContext;
import ua.edu.ukma.network.TcpServer;
import ua.edu.ukma.repository.InMemoryRepository;
import ua.edu.ukma.repository.Repository;
import ua.edu.ukma.service.*;

public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting server application");

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
        AuthService authService = new AuthService();

        TcpServer server = new TcpServer(5555, authService, facultyService, departmentService, specialtyService, studentService, teacherService);
        logger.info("Server initialized, starting listen loop");
        server.start();
    }
}