package ua.edu.ukma.service;

import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.repository.Repository;

import java.util.*;

public class FacultyService {

    private final Repository<Faculty, Integer> repo;

    public FacultyService(Repository<Faculty, Integer> repo) {
        this.repo = repo;
    }

    public void add(Faculty f) {
        validate(f);
        repo.save(f);
    }

    public Optional<Faculty> find(int id) {
        return repo.findById(id);
    }

    public Faculty getOrThrow(int id) {
        Optional<Faculty> opt = repo.findById(id);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Faculty with id " + id + " not found");
        }
        return opt.get();
    }

    public List<Faculty> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }


    public List<Faculty> sortedByName() {
        List<Faculty> result = new ArrayList<>(repo.findAll());
        result.sort(Comparator.comparing(Faculty::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    private void validate(Faculty f) {
        if (f == null) throw new ValidationException("Faculty cannot be null");
        if (f.getName() == null || f.getName().isBlank()) throw new ValidationException("Faculty name cannot be empty");
    }

    public void updatePartial(int id, Optional<String> name, Optional<String> shortName, Optional<Teacher> dean, Optional<String> contacts) {
        Faculty f = getOrThrow(id);
        if (name.isPresent()) f.setName(name.get());
        if (shortName.isPresent()) f.setShortName(shortName.get());
        if (dean.isPresent()) f.setDean(dean.get());
        if (contacts.isPresent()) f.setContacts(contacts.get());
        repo.save(f);
    }
}