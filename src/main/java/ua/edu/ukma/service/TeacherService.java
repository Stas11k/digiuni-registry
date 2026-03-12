package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.repository.Repository;

import java.time.LocalDate;
import java.util.*;

public class TeacherService {

    private final Repository<Teacher, Integer> repo;

    public TeacherService(Repository<Teacher, Integer> repo) {
        this.repo = repo;
    }

    public void add(Teacher t) {
        validate(t);
        repo.save(t);
    }

    public Teacher getOrThrow(int id) {
        Optional<Teacher> opt = repo.findById(id);
        if (opt.isEmpty()) throw new EntityNotFoundException("Teacher with id " + id + " not found");
        return opt.get();
    }

    public List<Teacher> getAll() {
        return repo.findAll();
    }

    public boolean delete(Integer id) {
        return repo.deleteById(id);
    }

    public List<Teacher> findByFullName(String query) {
        List<Teacher> result = new ArrayList<>();
        List<Teacher> all = repo.findAll();
        String search = query.trim().toLowerCase();
        for (int i = 0; i < all.size(); i++) {
            Teacher t = all.get(i);
            String fullName = t.getFullName().toLowerCase();
            if (fullName.contains(search)) result.add(t);
        }
        return result;
    }

    public List<Teacher> findByPosition(String position) {
        return repo.findAll().stream()
                .filter(t -> t.getPosition() != null && t.getPosition().equalsIgnoreCase(position))
                .toList();
    }

    public List<Teacher> findByFacultySortedByName(int facultyId) {
        List<Teacher> result = new ArrayList<>();
        List<Teacher> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Teacher t = all.get(i);
            if (t.getDepartment() != null
                    && t.getDepartment().getFaculty() != null
                    && t.getDepartment().getFaculty().getId() == facultyId) {
                result.add(t);
            }
        }
        result.sort(Comparator.comparing(Teacher::getLastName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Teacher::getFirstName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(t -> t.getMiddleName() == null ? "" : t.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );
        return result;
    }

    public List<Teacher> findByDepartmentSortedByName(int departmentId) {
        List<Teacher> result = new ArrayList<>();
        List<Teacher> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Teacher t = all.get(i);
            if (t.getDepartment() != null && t.getDepartment().getId() == departmentId) result.add(t);
        }
        result.sort(Comparator.comparing(Teacher::getLastName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Teacher::getFirstName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(t -> t.getMiddleName() == null ? "" : t.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );
        return result;
    }


    private void validate(Teacher t) {
        if (t == null) throw new ValidationException("Teacher cannot be null");
        if (t.getFirstName() == null || t.getFirstName().isBlank() || t.getLastName() == null || t.getLastName().isBlank()) throw new ValidationException("Name cannot be empty");
        if (t.getPosition() == null || t.getPosition().isBlank()) throw new ValidationException("Position required");
    }

    public void updatePartial(int id, Optional<String> lastName, Optional<String> firstName, Optional<String> middleName, Optional<String> birthDate, Optional<String> email, Optional<String> phone, Optional<String> address, Optional<String> position, Optional<Department> department, Optional<String> degree, Optional<String> title, Optional<LocalDate> hireDate, Optional<Double> workload) {
        Teacher t = getOrThrow(id);
        if (lastName.isPresent()) t.setLastName(lastName.get());
        if (firstName.isPresent()) t.setFirstName(firstName.get());
        if (middleName.isPresent()) t.setMiddleName(middleName.get());
        if (birthDate.isPresent()) {
            String value = birthDate.get().trim();
            if (value.isEmpty()) t.setBirthDate(null);
            else t.setBirthDate(LocalDate.parse(value));
        }
        if (email.isPresent()) t.setEmail(email.get());
        if (phone.isPresent()) t.setPhone(phone.get());
        if (address.isPresent()) t.setAddress(address.get());
        if (position.isPresent()) t.setPosition(position.get());
        if (department.isPresent()) t.setDepartment(department.get());
        if (degree.isPresent()) t.setDegree(degree.get());
        if (title.isPresent()) t.setTitle(title.get());
        if (hireDate.isPresent()) t.setHireDate(hireDate.get());
        if (workload.isPresent()) t.setWorkload(workload.get());
        repo.save(t);
    }
}
