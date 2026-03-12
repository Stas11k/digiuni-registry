package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.repository.InMemoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {

    private InMemoryRepository<Teacher, Integer> repo;
    private TeacherService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryRepository<>();
        service = new TeacherService(repo);
    }

    private Teacher createTeacher(String firstName, String lastName, String position) {

        Faculty faculty = new Faculty("FCS", "CS");
        Department department = new Department("SE", faculty);

        return new Teacher(
                lastName,
                firstName,
                "Test",
                position,
                department
        );
    }


    @Test
    void addAndGetTeacher() {
        Teacher teacher = createTeacher("Illia", "Kabysh", "teacher");
        service.add(teacher);
        assertEquals(1, service.getAll().size());
    }


    @Test
    void getOrThrowTeacherTest() {
        Teacher teacher = createTeacher("Ivan", "Petrenko", "Professor");
        repo.save(teacher);
        Teacher result = service.getOrThrow(teacher.getId());
        assertEquals("Petrenko", result.getLastName());
    }
    @Test
    void getOrThrow_shouldThrow() {

        assertThrows(EntityNotFoundException.class,
                () -> service.getOrThrow(100));
    }


    @Test
    void deleteTeacherTest() {
        Teacher teacher = createTeacher("Ivan", "Petrenko", "Professor");
        repo.save(teacher);
        repo.deleteById(teacher.getId());
        assertTrue(repo.findById(teacher.getId()).isEmpty());
    }


    @Test
    void findByFullNameTeacherTest() {
        Teacher teacher = createTeacher("Ivan", "Petrenko", "Professor");
        repo.save(teacher);
        List<Teacher> result = service.findByFullName("Iva");
        assertEquals(1, result.size());
    }
    @Test
    void findByPositionTeacherTest() {
        Teacher teacher = createTeacher("Ivan", "Petrenko", "Professor");
        repo.save(teacher);
        List<Teacher> result = service.findByPosition("Professor");
        assertEquals(1, result.size());

    }
    @Test
    void updatePartial() {

        Teacher teacher = createTeacher("Ivan","Petrenko","Professor");
        repo.save(teacher);

        service.updatePartial(
                teacher.getId(),
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
                Optional.empty()
        );

        Teacher updated = service.getOrThrow(teacher.getId());

        assertEquals("Shevchenko", updated.getLastName());
    }
    @ParameterizedTest
    @CsvSource({
            "Professor, 2",
            "Assistant, 1"
    })
    void findByPositionParameterized(String position, int expectedSize) {

        InMemoryRepository<Teacher, Integer> repo = new InMemoryRepository<>();
        TeacherService service = new TeacherService(repo);

        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("Software Engineering", faculty);

        repo.save(new Teacher("Petrenko", "Ivan", "A", "Professor", department));
        repo.save(new Teacher("Bondar", "Anna", "B", "Professor", department));
        repo.save(new Teacher("Koval", "Oleh", "C", "Assistant", department));

        List<Teacher> result = service.findByPosition(position);

        assertEquals(expectedSize, result.size());
    }
    @Test
    void findByDepartmentSortedByName() {
        Faculty faculty = new Faculty("CS", "CS");
        Department department = new Department("SE", faculty);

        repo.save(new Teacher("Petrenko", "Ivan", "A", "Professor", department));
        repo.save(new Teacher("Bondar", "Anna", "B", "Professor", department));

        List<Teacher> result = service.findByDepartmentSortedByName(department.getId());

        assertEquals("Bondar", result.get(0).getLastName());
    }

}