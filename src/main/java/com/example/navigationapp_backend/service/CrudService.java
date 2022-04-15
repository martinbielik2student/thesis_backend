package com.example.navigationapp_backend.service;

import java.util.List;

public interface CrudService<T,R> {

    T save(R r);

    void delete(Long id);

    T getById(Long id);

    List<T> getAll();
}
