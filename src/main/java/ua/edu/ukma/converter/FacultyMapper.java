package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.dto.FacultyDTO;

public class FacultyMapper {

    public static FacultyDTO toDTO(Faculty f) {
        return new FacultyDTO(
                f.getId(),
                f.getName(),
                f.getShortName(),
                f.getDean() != null ? f.getDean().getId() : null,
                f.getContacts()
        );
    }

    public static Faculty fromDTO(FacultyDTO dto) {
        Faculty faculty = new Faculty(dto.id(), dto.name(), dto.shortName());
        faculty.setContacts(dto.contacts());
        return faculty;
    }
}
