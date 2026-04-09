package ua.edu.ukma.dto;

public record StudentDTO(
        String lastName,
        String firstName,
        String middleName,
        int course,
        int group
) {}
