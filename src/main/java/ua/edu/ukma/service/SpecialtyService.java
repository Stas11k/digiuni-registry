package ua.edu.ukma.service;

import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyService {

    private final Repository<Specialty, Integer> repo;

    public SpecialtyService(Repository<Specialty, Integer> repo) {
        this.repo = repo;
    }

    public void add(Specialty specialty) {
        validate(specialty);
        repo.save(specialty);
    }

    public Specialty get(int id) {
        return repo.findById(id);
    }

    public List<Specialty> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }

    public List<Specialty> findByDepartment(int departmentId) {
        List<Specialty> result = new ArrayList<>();

        for (Specialty s : repo.findAll()) {
            if (s.getDepartment().getId() == departmentId) {
                result.add(s);
            }
        }
        return result;
    }

    private void validate(Specialty s) {
        if (s.getName() == null || s.getName().isBlank())
            throw new IllegalArgumentException("Specialty name cannot be empty");

        if (s.getDepartment() == null)
            throw new IllegalArgumentException("Department cannot be null");
    }
}
