package ua.edu.ukma.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    void findByFacultySortedByName() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("SE", faculty);
        Specialty specialty = new Specialty("CS", department);

        repo.save(new Student("Petrenko", "Ivan", "A", "101", 2,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 2, 123, specialty));

        List<Student> result = service.findByFacultySortedByName(faculty.getId());

        assertEquals("Bondar", result.get(0).getLastName());
    }
    @Test
    void findByDepartmentSortedByCourse() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("SE", faculty);
        Specialty specialty = new Specialty("CS", department);

        repo.save(new Student("Petrenko", "Ivan", "A", "101", 1,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 2, 123, specialty));

        List<Student> result = service.findByDepartmentSortedByCourse(department.getId());

        assertEquals(1, result.get(0).getCourse());
    }
    @Test
    void findByDepartmentSortedByName() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("SE", faculty);
        Specialty specialty = new Specialty("CS", department);

        repo.save(new Student("Petrenko", "Ivan", "A", "101", 1,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 2, 123, specialty));

        List<Student> result = service.findByDepartmentSortedByName(department.getId());

        assertEquals("Bondar", result.get(0).getLastName());
    }
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 1",
            "3, 1",
            "4, 1"
    })
    void findByCourseParameterized(int course, int expectedSize) {

        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);

        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("Software Engineering", faculty);
        Specialty specialty = new Specialty("Computer Science", department);

        repo.save(new Student("Petrenko", "Ivan", "A", "101", 1,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 2, 123, specialty));
        repo.save(new Student("Petrenko", "Ivan", "A", "101", 3,123, specialty));
        repo.save(new Student("Petrenko", "Ivan", "A", "101", 4,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 5, 123, specialty));

        List<Student> result = service.findByCourse(course);

        assertEquals(expectedSize, result.size());
    }
    @Test
    void findByDepartmentAndCourse() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("SE", faculty);
        Specialty specialty = new Specialty("CS", department);

        repo.save(new Student("Petrenko", "Ivan", "A", "101", 1,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 2, 123, specialty));

        List<Student> result = service.findByDepartmentAndCourse(department.getId(), 2);

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

    @Test
    void findByDepartmentAndCourseSortedByName() {
        InMemoryRepository<Student, Integer> repo = new InMemoryRepository<>();
        StudentService service = new StudentService(repo);
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("SE", faculty);
        Specialty specialty = new Specialty("CS", department);

        repo.save(new Student("Petrenko", "Ivan", "A", "101", 2,123, specialty));
        repo.save(new Student("Bondar", "Anna", "B", "102", 2, 123, specialty));

        List<Student> result =
                service.findByDepartmentAndCourseSortedByName(department.getId(), 2);

        assertEquals("Bondar", result.get(0).getLastName());
    }
}
