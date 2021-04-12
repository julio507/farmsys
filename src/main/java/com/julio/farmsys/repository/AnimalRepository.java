package com.julio.farmsys.repository;

import com.julio.farmsys.model.Animal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal,Long>{
    
}
