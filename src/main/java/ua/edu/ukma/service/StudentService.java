package ua.edu.ukma.service;

import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.StudentStatus;
import ua.edu.ukma.domain.StudyForm;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.repository.Repository;

import java.time.LocalDate;
import java.util.*;

public class StudentService {

    private final Repository<Student, Integer> repo;

    public StudentService(Repository<Student, Integer> repo) {
        this.repo = repo;
    }

    public void add(Student student) {
        validate(student);
        repo.save(student);
    }

    public Student getOrThrow(int id) {
        Optional<Student> opt = repo.findById(id);
        if (opt.isEmpty()) throw new EntityNotFoundException("Student with id " + id + " not found");
        return opt.get();
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public boolean delete(Integer id) {
        return repo.deleteById(id);
    }

    public List<Student> findByFullName(String query) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        String search = query.trim().toLowerCase();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            String fullName = s.getFullName().toLowerCase();
            if (fullName.contains(search)) result.add(s);
        }
        return result;
    }

    public List<Student> findByCourse(int course) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        String search = query.trim().toLowerCase();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            String fullName = s.getFullName().toLowerCase();
            if (fullName.contains(search)) result.add(s);
        }
        return result;
    }

    public List<Student> findByCourse(int course) {
        return repo.findAll().stream()
                .filter(s -> s.getCourse() == course)
                .toList();
    }

    public List<Student> findByGroup(int group) {
        return repo.findAll().stream()
                .filter(s -> s.getGroup() == group)
                .toList();
    }

    public List<Student> sortedByCourse() {
        List<Student> result = new ArrayList<>(repo.findAll());
        result.sort(Comparator.comparingInt(Student::getCourse));
        return result;
    }

    public List<Student> findByFacultySortedByName(int facultyId) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getSpecialty() != null
                    && s.getSpecialty().getDepartment() != null
                    && s.getSpecialty().getDepartment().getFaculty() != null
                    && s.getSpecialty().getDepartment().getFaculty().getId() == facultyId) {
                result.add(s);
            }
        }
        result.sort(Comparator.comparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(s -> s.getMiddleName() == null ? "" : s.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );

        return result;
    }

    public List<Student> sortedByCourse() {
        List<Student> result = new ArrayList<>(repo.findAll());
        result.sort(Comparator.comparingInt(Student::getCourse));
        return result;
    }

    public List<Student> findByFacultySortedByName(int facultyId) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getSpecialty() != null
                    && s.getSpecialty().getDepartment() != null
                    && s.getSpecialty().getDepartment().getFaculty() != null
                    && s.getSpecialty().getDepartment().getFaculty().getId() == facultyId) {
                result.add(s);
            }
        }
        result.sort(Comparator.comparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(s -> s.getMiddleName() == null ? "" : s.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );

        return result;
    }
    public List<Student> findByDepartmentSortedByCourse(int departmentId) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getSpecialty() != null
                    && s.getSpecialty().getDepartment() != null
                    && s.getSpecialty().getDepartment().getId() == departmentId) {
                result.add(s);
            }
        }
        result.sort(Comparator.comparingInt(Student::getCourse)
                        .thenComparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(s -> s.getMiddleName() == null ? "" : s.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );
        return result;
    }

    public List<Student> findByDepartmentSortedByName(int departmentId) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getSpecialty() != null
                    && s.getSpecialty().getDepartment() != null
                    && s.getSpecialty().getDepartment().getId() == departmentId) {
                result.add(s);
            }
        }
        result.sort(Comparator.comparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(s -> s.getMiddleName() == null ? "" : s.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );
        return result;
    }

    public List<Student> findByDepartmentAndCourse(int departmentId, int course) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getSpecialty() != null
                    && s.getSpecialty().getDepartment() != null
                    && s.getSpecialty().getDepartment().getId() == departmentId
                    && s.getCourse() == course) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> findByDepartmentAndCourseSortedByName(int departmentId, int course) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getSpecialty() != null
                    && s.getSpecialty().getDepartment() != null
                    && s.getSpecialty().getDepartment().getId() == departmentId
                    && s.getCourse() == course) {
                result.add(s);
            }
        }
        result.sort(Comparator.comparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Student::getFirstName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(s -> s.getMiddleName() == null ? "" : s.getMiddleName(), String.CASE_INSENSITIVE_ORDER)
        );
        return result;
    }

    private void validate(Student s) {
        if (s == null) throw new ValidationException("Student cannot be null");
        if (s.getFirstName() == null || s.getFirstName().isBlank() || s.getLastName() == null || s.getLastName().isBlank()) throw new ValidationException("Name cannot be empty");
        if (s.getCourse() < 1 || s.getCourse() > 6) throw new ValidationException("Invalid course");
        if (s.getGroup() <= 0) throw new ValidationException("Invalid group number");
    }

    public void updatePartial(int id, Optional<String> lastName, Optional<String> firstName, Optional<String> middleName, Optional<String> birthDate, Optional<String> email, Optional<String> phone, Optional<String> address, Optional<String> gradeBook, Optional<Integer> course, Optional<Integer> group, Optional<Specialty> specialty, Optional<Integer> admissionYear, Optional<StudyForm> studyForm, Optional<StudentStatus> status) {
        Student s = getOrThrow(id);
        if (lastName.isPresent()) s.setLastName(lastName.get());
        if (firstName.isPresent()) s.setFirstName(firstName.get());
        if (middleName.isPresent()) s.setMiddleName(middleName.get());
        if (birthDate.isPresent()) {
            String value = birthDate.get().trim();
            if (value.isEmpty()) s.setBirthDate(null);
            else s.setBirthDate(LocalDate.parse(value));
        }
        if (email.isPresent()) s.setEmail(email.get());
        if (phone.isPresent()) s.setPhone(phone.get());
        if (address.isPresent()) s.setAddress(address.get());
        if (gradeBook.isPresent()) s.setGradeBookNumber(gradeBook.get());
        if (course.isPresent()) s.setCourse(course.get());
        if (group.isPresent()) s.setGroup(group.get());
        if (specialty.isPresent()) s.setSpecialty(specialty.get());
        if (admissionYear.isPresent()) s.setAdmissionYear(admissionYear.get());
        if (studyForm.isPresent()) s.setStudyForm(studyForm.get());
        if (status.isPresent()) s.setStatus(status.get());
        repo.save(s);
    }
}