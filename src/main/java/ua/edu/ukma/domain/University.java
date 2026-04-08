package ua.edu.ukma.domain;

public record University(
        String fullName,
        String shortName,
        String city,
        String address
) {
    public University {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
    }
}
