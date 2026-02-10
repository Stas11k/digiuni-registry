package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryFacultyRepository;

import static org.junit.jupiter.api.Assertions.*;



class FacultyServiceTest {

    private FacultyService service;

    @BeforeEach
    void setUp() {
        service = new FacultyService(new InMemoryFacultyRepository());
    }

    @Test
    void addAndGetFaculty() {
        Faculty faculty = new Faculty("Computer Science", "CS");

        service.add(faculty);

        Faculty result = service.get(faculty.getId());
        assertNotNull(result);
        assertEquals("Computer Science", result.getName());
    }

    @Test
    void sortedByName_shouldReturnSortedList() {
        service.add(new Faculty("Law", "LAW"));
        service.add(new Faculty("Computer Science", "CS"));
        service.add(new Faculty("Economics", "ECO"));

        assertEquals(
                "Computer Science",
                service.sortedByName().get(0).getName()
        );
    }

    @Test
    void updateFaculty_shouldUpdateFields() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        service.add(faculty);
        Department department = new Department("Administration", faculty);

        Teacher dean = new Teacher(
                "Shevchenko",
                "Taras",
                "T.",
                "Dean",
                department
        );

        boolean updated = service.update(
                faculty.getId(),
                "Applied Sciences",
                "AS",
                dean,
                "as@ukma.edu.ua"
        );

        Faculty updatedFaculty = service.get(faculty.getId());

        assertTrue(updated);
        assertEquals("Applied Sciences", updatedFaculty.getName());
        assertEquals("AS", updatedFaculty.getShortName());
    }

    @Test
    void facultyConstructor_emptyName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Faculty("", "CS"));
    }
}