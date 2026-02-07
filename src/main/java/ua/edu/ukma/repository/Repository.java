package ua.edu.ukma.repository;

import java.util.List;

public interface Repository<T, ID> {

    void save(T entity);

    T findById(ID id);

    List<T> findAll();

    boolean deleteById(ID id);
}
