package ua.edu.ukma.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService service;


    @Test
    void addStudentTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        service.add(student);
        assertEquals(1, service.getAll().size());
    }


    @Test
    void getOrThrowStudentTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);

        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);

        Student result = service.getOrThrow(student.getId());

        assertEquals("Ivanov", result.getLastName());
    }
    @Test
    void getOrThrow_shouldThrow() {

        assertThrows(NullPointerException.class,
                () -> service.getOrThrow(100));
    }


    @Test
    void deleteStudent() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);
        repo.deleteById(student.getId());
        assertTrue(repo.findById(student.getId()).isEmpty());
    }


    @Test
    void findByCourseTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);
        List<Student> result = service.findByCourse(1);
        assertEquals(1, result.size());
    }


    @Test
    void findByGroupTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);
        List<Student> result = service.findByGroup(12);
        assertEquals(1, result.size());


    }


    @Test
    void sortedByCourseTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);
        List<Student> result = service.sortedByCourse();

        assertEquals(1, result.get(0).getCourse());
    }
    @Test
    void findByFullNameTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);
        List<Student> result = service.findByFullName("Iva");

        assertEquals(1, result.size());
    }
    @Test
    void updatePartialStudentTest() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("ComputerScience", "CS");
        Department department = new Department("SoftwareEngineering", faculty);
        Specialty specialty = new Specialty("ComputerScience", department);
        Student student = new Student(
                "Ivanov",
                "Ivan",
                "BOb",
                "123",
                1,
                12,
                specialty
        );
        repo.save(student);
        service.updatePartial(
                student.getId(),
                Optional.of("Shevchenko"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        Student updated = service.getOrThrow(student.getId());

        assertEquals("Shevchenko", updated.getLastName());
    }
}
