package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryTeacherRepository;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {

    private TeacherService service;

    @BeforeEach
    void setUp() {
        service = new TeacherService(new InMemoryTeacherRepository());
    }

    private Teacher createTeacher() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        return new Teacher(
                "Ivanenko",
                "Ivan",
                "I.",
                "Professor",
                department
        );
    }

    @Test
    void addAndGetTeacher() {
        Teacher teacher = createTeacher();
        service.add(teacher);

        Teacher result = service.get(teacher.getId());

        assertNotNull(result);
        assertEquals("Ivanenko", result.getLastName());
    }

    @Test
    void findByPosition_shouldReturnTeachers() {
        service.add(createTeacher());

        assertEquals(1, service.findByPosition("Professor").size());
    }

    @Test
    void updateTeacher_shouldUpdateFields() {
        Teacher teacher = createTeacher();
        service.add(teacher);

        Faculty faculty = new Faculty("Mathematics", "MATH");
        Department newDepartment = new Department("Applied Math", faculty);

        boolean updated = service.update(
                teacher.getId(),
                "Shevchenko",
                "Taras",
                "T.",
                "1980-01-01",
                "taras@mail.com",
                "123456789",
                "Kyiv",
                "Associate Professor",
                newDepartment,
                "PhD",
                "Docent",
                LocalDate.of(2010, 9, 1),
                1.0
        );

        Teacher updatedTeacher = service.get(teacher.getId());

        assertTrue(updated);
        assertEquals("Shevchenko", updatedTeacher.getLastName());
        assertEquals("Associate Professor", updatedTeacher.getPosition());
        assertEquals("PhD", updatedTeacher.getDegree());
    }

    @Test
    void teacherConstructor_emptyLastName_shouldThrowException() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        assertThrows(IllegalArgumentException.class,
                () -> new Teacher(
                        "",
                        "Ivan",
                        "I.",
                        "Professor",
                        department
                ));
    }
}