package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Faculty;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFacultyRepository implements Repository<Faculty, String> {

    private final List<Faculty> faculties = new ArrayList<>();

    @Override
    public void save(Faculty faculty) {
        faculties.add(faculty);
    }

    @Override
    public Faculty findById(String code) {
        for (Faculty f : faculties) {
            if (f.getCode().equals(code)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Faculty> findAll() {
        return new ArrayList<>(faculties);
    }

    @Override
    public boolean deleteById(String code) {
        for (Faculty f : faculties) {
            if (f.getCode().equals(code)) {
                faculties.remove(f);
                return true;
            }
        }
        return false;
    }
}
