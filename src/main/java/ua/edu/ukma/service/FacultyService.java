package ua.edu.ukma.service;

import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.Repository;

import java.util.*;

public class FacultyService {

    private final Repository<Faculty, Integer> repo;

    public FacultyService(Repository<Faculty, Integer> repo) {
        this.repo = repo;
    }


    public void add(Faculty f) {
        validate(f);
        repo.save(f);
    }


    public Faculty get(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Faculty> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }


    public List<Faculty> sortedByName() {
        List<Faculty> list = repo.findAll();

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getName()
                        .compareToIgnoreCase(list.get(j).getName()) > 0) {

                    Faculty tmp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, tmp);
                }
            }
        }
        return list;
    }

    private void validate(Faculty f) {
        if (f == null) throw new IllegalArgumentException("Faculty cannot be null");
        if (f.getName().isBlank()) throw new IllegalArgumentException("Faculty name empty");
    }

    public boolean update(int id, String name, String shortName, Teacher dean, String contacts) {
        Faculty f = repo.findById(id).orElse(null);
        if (f == null) return false;
        f.setName(name);
        f.setShortName(shortName);
        f.setDean(dean);
        f.setContacts(contacts);
        repo.save(f);
        return true;
    }
}
