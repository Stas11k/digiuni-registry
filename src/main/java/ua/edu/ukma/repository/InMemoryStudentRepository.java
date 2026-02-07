package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryStudentRepository implements Repository<Student, UUID> {

    private final List<Student> students = new ArrayList<>();

    @Override
    public void save(Student student) {
        students.add(student);
    }

    @Override
    public Student findById(UUID id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public boolean deleteById(UUID id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                students.remove(s);
                return true;
            }
        }
        return false;
    }
}
