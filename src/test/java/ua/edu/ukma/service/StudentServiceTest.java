package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.repository.InMemoryStudentRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService service;

    @BeforeEach
    void setUp() {
        InMemoryStudentRepository repo = new InMemoryStudentRepository();
        service = new StudentService(repo);
    }

    private Student validStudent() {
        Faculty f = new Faculty(
                "Computer Science",
                "CS"
        );

        Department d = new Department(
                "Programming",
                f
        );

        Specialty sp = new Specialty(
                "SE",           // name
                d               // department
        );

        return new Student(
                "Ivanov",
                "Ivan",
                "I.",
                "ST1",
                2,
                101,
                sp
        );
    }

    @Test
    void addStudent_shouldAddStudent() {
        service.add(validStudent());
        assertEquals(1, service.getAll().size());
    }

    @Test
    void findByCourse_shouldReturnCorrectStudents() {
        service.add(validStudent());
        assertEquals(1, service.findByCourse(2).size());
    }

    @Test
    void addStudent_invalidCourse_shouldThrowException() {
        Student s = validStudent();

        assertThrows(IllegalArgumentException.class,
                () -> s.setCourse(10));
    }

    @Test
    void addStudent_invalidGroup_shouldThrowException() {
        Student s = validStudent();
        s.setGroup(0);

        assertThrows(IllegalArgumentException.class, () -> service.add(s));
    }
}