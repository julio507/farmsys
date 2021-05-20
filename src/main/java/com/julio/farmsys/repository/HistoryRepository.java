package com.julio.farmsys.repository;

import java.util.Date;
import java.util.List;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.model.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h FROM History h WHERE h.animal = ?1 AND ( ?2 IS NULL OR ( ?3 IS NULL OR h.date BETWEEN ?2 AND ?3 ) ) "
            + " AND ( ?4 IS NULL OR ( ?5 IS NULL OR h.weight BETWEEN ?4 AND ?5 ) ) "
            + " AND ( ?6 IS NULL OR ( ?7 IS NULL OR h.height BETWEEN ?6 AND ?7 ) )")
    List<History> find(Animal animal, Date dateMin, Date dateMax, Double weightMin, Double weightMax, Double heightMin,
            Double heightMax);
}
