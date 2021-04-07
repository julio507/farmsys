package com.julio.farmsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class DefaultService<T> {

    @Autowired
    private JpaRepository<T, Long> repository;

    public T create(T t) {
        return repository.save(t);
    }

    public T update( T t ) {
        return null;
    }
}
