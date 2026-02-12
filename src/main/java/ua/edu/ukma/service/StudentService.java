package ua.edu.ukma.service;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.StudentStatus;
import ua.edu.ukma.domain.StudyForm;
import ua.edu.ukma.repository.InMemoryStudentRepository;

import java.util.*;
import ua.edu.ukma.domain.Specialty;

public class StudentService {
    private final InMemoryStudentRepository repo;
    public StudentService(InMemoryStudentRepository repo) {
        this.repo = repo;
    }
    public void add(Student student) {
        validate(student);
        repo.save(student);
    }
    public Student get(Integer id) {
        return repo.findById(id);
    }
    public List<Student> getAll() {
        return repo.findAll();
    }
    public boolean delete(Integer id) {
        return repo.deleteById(id);
    }
    public List<Student> findByCourse(int course) {
        List<Student> result = new ArrayList<>();

        for (Student s : repo.findAll()) {
            if (s.getCourse() == course) {
                result.add(s);
            }
        }
        return result;
    }
    public List<Student> findByGroup(int group) {
        List<Student> result = new ArrayList<>();

        for (Student s : repo.findAll()) {
            if (s.getGroup() == group) {
                result.add(s);
            }
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

        if (s.getFirstName().isBlank() || s.getLastName().isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        if (s.getCourse() < 1 || s.getCourse() > 6)
            throw new IllegalArgumentException("Invalid course");

        if (s.getGroup() <= 0)
            throw new IllegalArgumentException("Invalid group number");
    }

    public boolean update(int id, String lastName, String firstName, String middleName, String birthDate, String email, String phone, String address, String gradeBook, int course, int group, Specialty specialty, int admissionYear, StudyForm studyForm, StudentStatus status) {
        Student s = repo.findById(id);
        if (s == null) return false;
        s.setLastName(lastName);
        s.setFirstName(firstName);
        s.setMiddleName(middleName);
        s.setBirthDate(birthDate);
        s.setEmail(email);
        s.setPhone(phone);
        s.setAddress(address);
        s.setGradeBookNumber(gradeBook);
        s.setCourse(course);
        s.setGroup(group);
        s.setSpecialty(specialty);
        s.setAdmissionYear(admissionYear);
        s.setStudyForm(studyForm);
        s.setStatus(status);
        return true;
    }
}
