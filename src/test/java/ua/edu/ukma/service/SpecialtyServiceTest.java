package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.repository.InMemorySpecialtyRepository;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest {

    private SpecialtyService service;

    @BeforeEach
    void setUp() {
        service = new SpecialtyService(new InMemorySpecialtyRepository());
    }

    @Test
    void addAndGetSpecialty() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);
        Specialty specialty = new Specialty("Software Engineering", department);

        service.add(specialty);

        Specialty result = service.get(specialty.getId());
        assertNotNull(result);
        assertEquals("Software Engineering", result.getName());
    }

    @Test
    void findByDepartment_shouldReturnSpecialties() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        service.add(new Specialty("Software Engineering", department));
        service.add(new Specialty("Computer Science", department));

        assertEquals(2, service.findByDepartment(department.getId()).size());
    }

    @Test
    void updateSpecialty_shouldUpdateFields() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);
        Specialty specialty = new Specialty("SE", department);
        service.add(specialty);

        Department newDepartment = new Department("Applied Math", faculty);

        boolean updated = service.update(
                specialty.getId(),
                "Applied Software Engineering",
                newDepartment
        );

        Specialty updatedSpecialty = service.get(specialty.getId());

        assertTrue(updated);
        assertEquals("Applied Software Engineering", updatedSpecialty.getName());
        assertEquals(newDepartment, updatedSpecialty.getDepartment());
    }

    @Test
    void specialtyConstructor_emptyName_shouldThrowException() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        assertThrows(IllegalArgumentException.class,
                () -> new Specialty("", department));
    }
    @Test
    void updateSpecialty_nonExistingId_shouldReturnFalse() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        boolean result = service.update(
                999,
                "Physics",
                department
        );

        assertFalse(result);
    }
}
