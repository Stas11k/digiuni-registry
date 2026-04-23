package ua.edu.ukma.service;

import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.io.AsyncSaveService;
import ua.edu.ukma.io.DataContext;
import ua.edu.ukma.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {
    InMemoryRepository<Faculty, Integer> facultyRepo = new InMemoryRepository<>();
    InMemoryRepository<Department, Integer> departmentRepo = new InMemoryRepository<>();
    InMemoryRepository<Specialty, Integer> specialtyRepo = new InMemoryRepository<>();
    InMemoryRepository<Teacher, Integer> teacherRepo = new InMemoryRepository<>();
    InMemoryRepository<Student, Integer> studentRepo = new InMemoryRepository<>();

    University university = new University("NaUKMA", "NaUKMA", "Kyiv", "Address");

    DataContext dataContext = new DataContext(
            facultyRepo,
            departmentRepo,
            specialtyRepo,
            teacherRepo,
            studentRepo,
            university
    );
    AsyncSaveService saveService = new AsyncSaveService() {
        @Override
        public CompletableFuture<Void> saveAsync(DataContext dataContext) {

            return CompletableFuture.completedFuture(null);
        }
    };
    private DepartmentService service;

    @Test
    void addAndGetDepartment() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(departmentRepo,saveService,dataContext);
        Faculty faculty = new Faculty("Software engeneering", "SE");
        Department department = new Department("Information Technology", faculty);
        service.add(department);
        assertTrue(service.find(department.getId()).isPresent());
        assertEquals("Information Technology", service.find(department.getId()).get().getName());
    }


    @Test
    void findDepartmentById() {
        DepartmentService service = new DepartmentService(departmentRepo,saveService,dataContext);

        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Information Technology", faculty);

        departmentRepo.save(department);

        assertTrue(service.find(department.getId()).isPresent());

    }


    @Test
    void getOrThrow_shouldThrowException() {
        DepartmentService service = new DepartmentService(departmentRepo,saveService,dataContext);
        assertThrows(RuntimeException.class, () -> service.getOrThrow(100));

    }


    @Test
    void deleteDepartmentById() {
        DepartmentService service = new DepartmentService(departmentRepo,saveService,dataContext);
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Information Technology", faculty);
        departmentRepo.save(department);
        service.delete(department.getId());
        assertTrue(service.find(department.getId()).isEmpty());
    }
    @Test
    void findByFacultyId() {
        DepartmentService service = new DepartmentService(departmentRepo,saveService,dataContext);
        Faculty faculty1 = new Faculty("Computer Science", "CS");
        Faculty faculty2 = new Faculty("Information ipz", "IPZ");
        Department department1 = new Department("Information Technology", faculty1);
        Department department2 = new Department("Information Technology", faculty2);
        departmentRepo.save(department1);
        departmentRepo.save(department2);
        List<Department> result = service.findByFaculty(faculty1.getId());
        assertEquals(1, result.size());
    }
    @Test
    void updatePartialTest() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(departmentRepo,saveService,dataContext);

        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("Informat", faculty);

        departmentRepo.save(department);

        service.updatePartial(
                department.getId(),
                Optional.of("NewName"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        Department updated = service.getOrThrow(department.getId());

        assertEquals("NewName", updated.getName());
    }
}