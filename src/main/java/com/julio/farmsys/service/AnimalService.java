package com.julio.farmsys.service;

import java.util.Date;
import java.util.List;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.repository.AnimalRepository;

import org.springframework.stereotype.Service;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAll() {
        return animalRepository.findAll();
    }

    public List<Animal> getAll(String species, Date bornDateMin, Date bornDateMax, Date aquisitionDateMin,
            Date aquisitioDateMax, Double weightMin, Double weightMax, Double heightMin, Double heightMax,
            boolean active) {
        return animalRepository.find(species, bornDateMin, bornDateMax, aquisitionDateMin, aquisitioDateMax, weightMin,
                weightMax, heightMin, heightMax, active);
    }

    public Animal getById(long id) {
        return animalRepository.findById(id).get();
    }

    public void save(Animal a) {
        animalRepository.save(a);
    }
}
