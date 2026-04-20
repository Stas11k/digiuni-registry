package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.dto.DepartmentDTO;

public class DepartmentMapper {

    public static DepartmentDTO toDTO(Department d) {
        return new DepartmentDTO(
                d.getId(),
                d.getName(),
                d.getFaculty().getId(),
                d.getHead() != null ? d.getHead().getId() : null,
                d.getLocation()
        );
    }

    public static Department fromDTO(DepartmentDTO dto, Faculty faculty) {
        Department department = new Department(dto.id(), dto.name(), faculty);
        department.setLocation(dto.location());
        return department;
    }
}