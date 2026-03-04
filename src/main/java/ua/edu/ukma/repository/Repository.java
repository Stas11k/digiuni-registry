package ua.edu.ukma.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean deleteById(ID id);

    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    default int count() {
        return findAll().size();
    }
}
