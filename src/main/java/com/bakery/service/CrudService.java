package com.bakery.service;

import java.util.List;
import java.util.Optional;

/**
 * Generic CRUD service INTERFACE — core of OOP design.
 * Enables polymorphic usage across all entity types.
 */
public interface CrudService<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    T update(T entity);
    void deleteById(ID id);
    boolean existsById(ID id);
}
