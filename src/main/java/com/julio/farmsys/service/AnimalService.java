package com.julio.farmsys.service;

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

    public Animal getById(long id) {
        return animalRepository.getOne(id);
    }

    public void save( Animal a ){
        animalRepository.save( a );
    }
}
