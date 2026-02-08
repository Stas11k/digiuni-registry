package ua.edu.ukma.service;

import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.Repository;

import java.util.*;

public class TeacherService {

    private final Repository<Teacher, UUID> repo;

    public TeacherService(Repository<Teacher, UUID> repo) {
        this.repo = repo;
    }

    public void add(Teacher t) {
        validate(t);
        repo.save(t);
    }

    public Teacher get(UUID id) {
        return repo.findById(id);
    }

    public List<Teacher> getAll() {
        return repo.findAll();
    }

    public boolean delete(UUID id) {
        return repo.deleteById(id);
    }

    public List<Teacher> findByPosition(String position) {
        return repo.findAll().stream()
                .filter(t -> t.getPosition().equalsIgnoreCase(position))
                .toList();
    }

    private void validate(Teacher t) {
        if (t.getFirstName().isBlank() || t.getLastName().isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        if (t.getPosition().isBlank())
            throw new IllegalArgumentException("Position required");
    }
}
