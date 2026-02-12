package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryDepartmentRepository;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {
    private DepartmentService service;

    @BeforeEach
    void setUp() {
        service = new DepartmentService(new InMemoryDepartmentRepository());
    }

    @Test
    void addAndGetDepartment() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        service.add(department);

        Department result = service.get(department.getId());
        assertNotNull(result);
        assertEquals("Programming", result.getName());
    }

    @Test
    void findByFaculty() {
        Faculty faculty = new Faculty("Computer Science", "CS");

        service.add(new Department("Programming", faculty));
        service.add(new Department("Databases", faculty));

        assertEquals(2, service.findByFaculty(faculty.getId()).size());
    }

    @Test
    void updateDepartment() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);
        service.add(department);

        Teacher head = new Teacher(
                "Ivanenko",
                "Ivan",
                "I.",
                "Professor",
                department
        );

        boolean updated = service.update(
                department.getId(),
                "Applied Programming",
                faculty,
                head,
                "Room 201"
        );

        assertTrue(updated);
        assertEquals("Applied Programming", service.get(department.getId()).getName());
    }

    @Test
    void departmentConstructor_emptyName_shouldThrowException() {
        Faculty faculty = new Faculty("Computer Science", "CS");

        assertThrows(IllegalArgumentException.class,
                () -> new Department("", faculty));
    }
}