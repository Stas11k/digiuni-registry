package ua.edu.ukma.converter;

import ua.edu.ukma.domain.University;
import ua.edu.ukma.dto.UniversityDTO;

public class UniversityMapper {
    public static UniversityDTO toDTO(University u) {
        return new UniversityDTO(
                u.getFullName(),
                u.getShortName(),
                u.getCity(),
                u.getAddress()
        );
    }

    public static University fromDTO(UniversityDTO dto) {
        return new University(
                dto.fullName(),
                dto.shortName(),
                dto.city(),
                dto.address()
        );
    }
}