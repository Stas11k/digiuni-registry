package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.repository.InMemoryDepartmentRepository;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {

    private DepartmentService service;

    @BeforeEach
    void setUp() {
        InMemoryDepartmentRepository repo = new InMemoryDepartmentRepository();
        service = new DepartmentService(repo);
    }

    // helper: валідна кафедра
    private Department validDepartment() {
        Faculty faculty = new Faculty(
                "Computer Science",
                "CS"
        );

        return new Department(
                "Programming",
                faculty
        );
    }

    @Test
    void addDepartment_shouldAddDepartment() {
        service.add(validDepartment());

        assertEquals(1, service.getAll().size());
    }

    @Test
    void getAll_shouldReturnAllDepartments() {
        service.add(validDepartment());
        service.add(new Department("Databases",
                new Faculty("Math", "MATH")));

        assertEquals(2, service.getAll().size());
    }

    @Test
    void deleteDepartment_shouldRemoveDepartment() {
        Department d = validDepartment();
        service.add(d);

        service.delete(d.getId());

        assertEquals(0, service.getAll().size());
    }

    @Test
    void findByFaculty_shouldReturnDepartments() {
        Faculty faculty = new Faculty("Computer Science", "CS");

        Department d1 = new Department("Programming", faculty);
        Department d2 = new Department("Algorithms", faculty);

        service.add(d1);
        service.add(d2);

        assertEquals(2, service.findByFaculty(faculty.getId()).size());
    }

    @Test
    void addDepartment_emptyName_shouldThrowException() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department d = new Department("", faculty);

        assertThrows(IllegalArgumentException.class,
                () -> service.add(d));
    }
}