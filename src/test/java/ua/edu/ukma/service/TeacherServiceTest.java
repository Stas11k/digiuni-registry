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
        InMemoryTeacherRepository repo = new InMemoryTeacherRepository();
        service = new TeacherService(repo);
    }


    private Teacher validTeacher() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        return new Teacher(
                "Petrenko",
                "Petro",
                "P.",
                "Professor",
                department
        );
    }

    @Test
    void addTeacher_validTeacher_shouldAddTeacher() {
        service.add(validTeacher());

        assertEquals(1, service.getAll().size());
    }

    @Test
    void addTeacher_emptyPosition_shouldThrowException() {
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("Programming", faculty);

        Teacher t = new Teacher(
                "Petrenko",
                "Petro",
                "P.",
                "",
                department
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.add(t));
    }

    @Test
    void addTeacher_emptyFirstName_shouldThrowException() {

        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        Teacher t = new Teacher(
                "Petrenko",
                "",
                "P.",
                "Professor",
                department
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.add(t));
    }
}