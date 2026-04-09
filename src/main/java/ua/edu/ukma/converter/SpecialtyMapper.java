package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.dto.SpecialtyDTO;

public class SpecialtyMapper {

    public static SpecialtyDTO toDTO(Specialty s) {
        return new SpecialtyDTO(
                s.getId(),
                s.getName(),
                s.getDepartment() != null ? s.getDepartment().getName() : null
        );
    }

    public static Specialty fromDTO(SpecialtyDTO dto) {
        Department dep = new Department(dto.departmentName(), null);

        return new Specialty(
                dto.name(),
                dep
        );
    }
}
