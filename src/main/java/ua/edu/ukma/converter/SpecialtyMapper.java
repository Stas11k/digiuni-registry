package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.dto.SpecialtyDTO;

public class SpecialtyMapper {

    public static SpecialtyDTO toDTO(Specialty s) {
        return new SpecialtyDTO(
                s.getId(),
                s.getName(),
                s.getDepartment().getId()
        );
    }

    public static Specialty fromDTO(SpecialtyDTO dto, Department department) {
        return new Specialty(dto.id(), dto.name(), department);
    }
}