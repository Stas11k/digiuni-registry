package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.dto.FacultyDTO;

public class FacultyMapper {

    public static FacultyDTO toDTO(Faculty f) {
        return new FacultyDTO(
                f.getId(),
                f.getName(),
                f.getShortName()
        );
    }

    public static Faculty fromDTO(FacultyDTO dto) {
        return new Faculty(
                dto.name(),
                dto.shortName()
        );
    }
}
