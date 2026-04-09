package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.dto.DepartmentDTO;

public class DepartmentMapper {

    public static DepartmentDTO toDTO(Department d) {
        return new DepartmentDTO(
                d.getId(),
                d.getName(),
                d.getFaculty() != null ? d.getFaculty().getName() : null
        );
    }

    public static Department fromDTO(DepartmentDTO dto) {
        Faculty faculty = new Faculty(dto.facultyName(), "N/A");

        return new Department(
                dto.name(),
                faculty
        );
    }
}
