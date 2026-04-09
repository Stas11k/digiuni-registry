package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.dto.TeacherDTO;

public class TeacherMapper {

    public static TeacherDTO toDTO(Teacher t) {
        return new TeacherDTO(
                t.getLastName(),
                t.getFirstName(),
                t.getMiddleName(),
                t.getPosition()
        );
    }

    public static Teacher fromDTO(TeacherDTO dto) {

        Faculty faculty = new Faculty("Default Faculty", "DF");
        Department department = new Department("Default Department", faculty);

        return new Teacher(
                dto.lastName(),
                dto.firstName(),
                dto.middleName(),
                dto.position(),
                department
        );
    }
}
