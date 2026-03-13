package ua.edu.ukma.domain;

import org.junit.jupiter.api.Test;
import ua.edu.ukma.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void constructor_validData_shouldCreateDepartment() {
        Faculty faculty = new Faculty("Computer Science", "CS");

        Department department = new Department("Programming", faculty);

        assertNotNull(department);
        assertEquals("Programming", department.getName());
        assertEquals(faculty, department.getFaculty());
    }
    @Test
    void constructor_emptyName_shouldThrowException() {
        Faculty faculty = new Faculty("Computer Science", "CS");

        assertThrows(ValidationException.class,
                () -> new Department("", faculty));
    }
    @Test
    void setFaculty_nullFaculty_shouldThrowException() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department department = new Department("Programming", faculty);

        assertThrows(IllegalArgumentException.class,
                () -> department.setFaculty(null));
    }

}
