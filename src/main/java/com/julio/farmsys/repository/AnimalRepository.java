package com.julio.farmsys.repository;

import java.util.Date;
import java.util.List;

import com.julio.farmsys.model.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.specie LIKE %?1% AND ( ?2 IS NULL OR ( ?3 IS NULL OR a.bornDate BETWEEN ?2 AND ?3 ) )" )
    List<Animal> find(String species, Date bornDateMin, Date bornDateMax, Date aquisitionDateMin, Date aquisitioDateMax,
            Double weightMin, Double weightMax, Double heightMin, Double heightMax, boolean active);
}
