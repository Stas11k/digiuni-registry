package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {
    private DepartmentService service;


    @Test
    void addAndGetDepartment() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(repo);
        Faculty faculty = new Faculty("Software engeneering", "SE");
        Department department = new Department("Information Technology", faculty);
        service.add(department);
        assertTrue(service.find(department.getId()).isPresent());
        assertEquals("Information Technology", service.find(department.getId()).get().getName());
    }


    @Test
    void findDepartmentById() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(repo);

        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Information Technology", faculty);

        repo.save(department);

        assertTrue(service.find(department.getId()).isPresent());


    }


    @Test
    void getOrThrow_shouldThrowException() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(repo);
        assertThrows(RuntimeException.class, () -> service.getOrThrow(100));

    }


    @Test
    void deleteDepartmentById() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(repo);
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Information Technology", faculty);
        repo.save(department);
        service.delete(department.getId());
        assertTrue(service.find(department.getId()).isEmpty());
    }
    @Test
    void findByFacultyId() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(repo);
        Faculty faculty1 = new Faculty("Computer Science", "CS");
        Faculty faculty2 = new Faculty("Information ipz", "IPZ");
        Department department1 = new Department("Information Technology", faculty1);
        Department department2 = new Department("Information Technology", faculty2);
        repo.save(department1);
        repo.save(department2);
        List<Department> result = service.findByFaculty(faculty1.getId());
        assertEquals(1, result.size());
    }
    @Test
    void updatePartialTest() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        DepartmentService service = new DepartmentService(repo);

        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("Informat", faculty);

        repo.save(department);

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