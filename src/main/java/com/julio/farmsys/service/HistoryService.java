package com.julio.farmsys.service;

import java.util.Date;
import java.util.List;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.model.History;
import com.julio.farmsys.repository.HistoryRepository;

import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<History> getAll() {
        return historyRepository.findAll();
    }

    public List<History> getAll(Animal animal, Date dateMin, Date dateMax, Double weightMin, Double weightMax,
            Double heightMin, Double heightMax) {
        return historyRepository.find(animal, dateMin, dateMax, weightMin, weightMax, heightMin, heightMax);
    }

    public void save( History h){
        historyRepository.save( h );
    }
}
