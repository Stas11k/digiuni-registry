package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialtyService {

    private final Repository<Specialty, Integer> repo;

    public SpecialtyService(Repository<Specialty, Integer> repo) {
        this.repo = repo;
    }

    public void add(Specialty specialty) {
        validate(specialty);
        repo.save(specialty);
    }

    public Optional<Specialty> find(int id) {
        return repo.findById(id);
    }

    public Specialty getOrThrow(int id) {
        Optional<Specialty> opt = repo.findById(id);
        if (opt.isEmpty()) throw new EntityNotFoundException("Specialty with id " + id + " not found");
        return opt.get();
    }

    public List<Specialty> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }

    public List<Specialty> findByDepartment(int departmentId) {
        List<Specialty> result = new ArrayList<>();
        List<Specialty> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Specialty s = all.get(i);
            if (s.getDepartment() != null && s.getDepartment().getId() == departmentId) result.add(s);
        }
        return result;
    }

    private void validate(Specialty s) {
        if (s == null) throw new ValidationException("Specialty cannot be null");
        if (s.getName() == null || s.getName().isBlank()) throw new ValidationException("Specialty name cannot be empty");
        if (s.getDepartment() == null) throw new ValidationException("Department cannot be null");
    }

    public void updatePartial(int id, Optional<String> name, Optional<Department> department) {
        Specialty s = getOrThrow(id);
        if (name.isPresent()) s.setName(name.get());
        if (department.isPresent()) s.setDepartment(department.get());
        repo.save(s);
    }
}
