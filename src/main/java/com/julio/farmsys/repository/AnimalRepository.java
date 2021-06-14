package com.julio.farmsys.repository;

import java.util.Date;
import java.util.List;

import com.julio.farmsys.model.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

        @Query("SELECT a FROM Animal a WHERE a.specie LIKE %?11% AND ( ?2 IS NULL OR ( ?3 IS NULL OR a.bornDate BETWEEN ?2 AND ?3 ) ) "
                        + "AND ( ?4 IS NULL OR ( ?5 IS NULL OR a.acquisitionDate BETWEEN ?4 AND ?5 ) ) "
                        + "AND ( ?6 IS NULL OR ( ?7 IS NULL OR a.weight BETWEEN ?6 AND ?7 ) ) "
                        + "AND ( ?8 IS NULL OR ( ?9 IS NULL OR a.height BETWEEN ?8 AND ?9 ) ) AND a.active = ?10 AND a.identification LIKE %?1%")
        List<Animal> find(String species, Date bornDateMin, Date bornDateMax, Date aquisitionDateMin,
                        Date aquisitioDateMax, Double weightMin, Double weightMax, Double heightMin, Double heightMax,
                        boolean active, String identification);
}
