package com.julio.farmsys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.model.History;
import com.julio.farmsys.repository.AnimalRepository;
import com.julio.farmsys.repository.HistoryRepository;

import org.springframework.stereotype.Service;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final HistoryRepository historyRepository;

    public AnimalService(AnimalRepository animalRepository, HistoryRepository historyRepository) {
        this.animalRepository = animalRepository;
        this.historyRepository = historyRepository;
    }

    public List<Animal> getAll() {
        return animalRepository.findAll();
    }

    public List<Animal> getAll(String identification, String species, Date bornDateMin, Date bornDateMax, Date aquisitionDateMin,
            Date aquisitioDateMax, Double weightMin, Double weightMax, Double heightMin, Double heightMax,
            boolean active) {
        List<Animal> animals = animalRepository.find(species, bornDateMin, bornDateMax, aquisitionDateMin,
                aquisitioDateMax, weightMin, weightMax, heightMin, heightMax, active, identification);

        for (Animal a : animals) {
            History h = historyRepository.findLatesHistory( a, weightMin, weightMax, heightMin, heightMax);

            a.setHeight( h != null ? h.getHeight() : 0 );
            a.setWeight( h != null ? h.getWeight() : 0 );
        }

        return animals;
    }

    public Animal getById(long id) {
        return animalRepository.findById(id).get();
    }

    public void save(Animal a) {
        animalRepository.save(a);
    }
}
