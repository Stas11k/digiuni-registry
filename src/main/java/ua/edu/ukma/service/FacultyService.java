package ua.edu.ukma.service;

import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.repository.Repository;

import java.util.*;

public class FacultyService {

    private final Repository<Faculty, Integer> repo;

    public FacultyService(Repository<Faculty, Integer> repo) {
        this.repo = repo;
    }

    // CREATE
    public void add(Faculty f) {
        validate(f);
        repo.save(f);
    }

    // READ
    public Faculty get(int id) {
        return repo.findById(id);
    }

    public List<Faculty> getAll() {
        return repo.findAll();
    }

    // DELETE
    public boolean delete(int id) {
        return repo.deleteById(id);
    }

    // REPORT
    public List<Faculty> sortedByName() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(Faculty::getName))
                .toList();
    }

    private void validate(Faculty f) {
        if (f.getName().isBlank())
            throw new IllegalArgumentException("Faculty name empty");
    }
}
