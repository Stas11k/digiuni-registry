package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.io.*;
import ua.edu.ukma.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialtyService {

    private final Repository<Specialty, Integer> repo;
    private final AsyncSaveService saveService;
    private final DataContext dataContext;

    public SpecialtyService(Repository<Specialty, Integer> repo, AsyncSaveService saveService, DataContext dataContext) {
        this.repo = repo;
        this.saveService = saveService;
        this.dataContext = dataContext;
    }

    public void add(Specialty specialty) {
        validate(specialty);
        repo.save(specialty);
        saveAll();
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
        Specialty specialty = repo.findById(id).orElse(null);
        if (specialty == null) {
            return false;
        }
        List<Integer> studentIdsToDelete = new ArrayList<>();
        for (Student s : dataContext.studentRepo().findAll()) {
            if (s.getSpecialty() != null && s.getSpecialty().getId() == id) {
                studentIdsToDelete.add(s.getId());
            }
        }
        for (Integer studentId : studentIdsToDelete) {
            dataContext.studentRepo().deleteById(studentId);
        }
        boolean deleted = repo.deleteById(id);
        if (deleted) saveAll();
        return deleted;
    }

    public List<Specialty> findByDepartment(int departmentId) {
        return repo.findAll().stream()
                .filter(s -> s.getDepartment() != null && s.getDepartment().getId() == departmentId)
                .toList();
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
        saveAll();
    }

    private void saveAll() {
        saveService.saveAsync(dataContext).join();
    }
}