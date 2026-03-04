package ua.edu.ukma.service;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.StudentStatus;
import ua.edu.ukma.domain.StudyForm;

import java.util.*;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.repository.Repository;

public class StudentService {

    private final Repository<Student, Integer> repo;

    public StudentService(Repository<Student, Integer> repo) {
        this.repo = repo;
    }

    public void add(Student student) {
        validate(student);
        repo.save(student);
    }

    public Optional<Student> find(int id) {
        return repo.findById(id);
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

    public List<Student> findByCourse(int course) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getCourse() == course) result.add(s);
        }
        return result;
    }

    public List<Student> findByGroup(int group) {
        List<Student> result = new ArrayList<>();
        List<Student> all = repo.findAll();
        for (int i = 0; i < all.size(); i++) {
            Student s = all.get(i);
            if (s.getGroup() == group) result.add(s);
        }
        return result;
    }

    public List<Student> sortedByCourse() {
        List<Student> result = repo.findAll();

        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(i).getCourse() > result.get(j).getCourse()) {
                    Student tmp = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, tmp);
                }
            }
        }
        return result;
    }

    private void validate(Student s) {
        if (s == null) throw new ValidationException("Student cannot be null");
        if (s.getFirstName() == null || s.getFirstName().isBlank()
                || s.getLastName() == null || s.getLastName().isBlank()) throw new ValidationException("Name cannot be empty");
        if (s.getCourse() < 1 || s.getCourse() > 6) throw new ValidationException("Invalid course");
        if (s.getGroup() <= 0) throw new ValidationException("Invalid group number");
    }

    public void updatePartial(int id, Optional<String> lastName, Optional<String> firstName, Optional<String> middleName, Optional<String> birthDate, Optional<String> email, Optional<String> phone, Optional<String> address, Optional<String> gradeBook, Optional<Integer> course, Optional<Integer> group, Optional<Specialty> specialty, Optional<Integer> admissionYear, Optional<StudyForm> studyForm, Optional<StudentStatus> status) {
        Student s = getOrThrow(id);
        if (lastName.isPresent()) s.setLastName(lastName.get());
        if (firstName.isPresent()) s.setFirstName(firstName.get());
        if (middleName.isPresent()) s.setMiddleName(middleName.get());
        if (birthDate.isPresent()) s.setBirthDate(birthDate.get());
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
