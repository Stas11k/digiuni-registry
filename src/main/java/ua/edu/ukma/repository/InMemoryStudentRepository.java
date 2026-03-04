package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class InMemoryStudentRepository{

    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public Student findById(Integer id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public boolean deleteById(Integer id) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
