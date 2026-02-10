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
        service = new StudentService(new InMemoryStudentRepository());
    }

    private Student createStudent() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);
        Specialty specialty = new Specialty("Software Engineering", department);

        return new Student(
                "Ivanov",
                "Ivan",
                "I.",
                "ST123",
                2,
                101,
                specialty
        );
    }

    @Test
    void addAndGetStudent() {
        Student student = createStudent();
        service.add(student);

        Student result = service.get(student.getId());

        assertNotNull(result);
        assertEquals("Ivanov", result.getLastName());
    }

    @Test
    void findByCourse_shouldReturnStudents() {
        service.add(createStudent());

        assertEquals(1, service.findByCourse(2).size());
    }

    @Test
    void findByGroup_shouldReturnStudents() {
        service.add(createStudent());

        assertEquals(1, service.findByGroup(101).size());
    }

    @Test
    void sortedByCourse_shouldSortStudents() {
        Student s1 = createStudent();
        Student s2 = createStudent();
        s2.setCourse(1);

        service.add(s1);
        service.add(s2);

        assertEquals(1, service.sortedByCourse().get(0).getCourse());
    }

    @Test
    void updateStudent_shouldUpdateFields() {
        Student student = createStudent();
        service.add(student);

        boolean updated = service.update(
                student.getId(),
                "Petrenko",
                "Petro",
                "P.",
                "2002-01-01",
                "petro@mail.com",
                "123456789",
                "Kyiv",
                "ST999",
                3,
                202,
                student.getSpecialty(),
                2021,
                StudyForm.BUDGET,
                StudentStatus.STUDYING
        );

        Student updatedStudent = service.get(student.getId());

        assertTrue(updated);
        assertEquals("Petrenko", updatedStudent.getLastName());
        assertEquals(3, updatedStudent.getCourse());
    }

    @Test
    void studentSetCourse_invalidValue_shouldThrowException() {
        Student student = createStudent();

        assertThrows(IllegalArgumentException.class,
                () -> student.setCourse(10));
    }
}