package com.julio.farmsys.controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.model.History;
import com.julio.farmsys.service.AnimalService;
import com.julio.farmsys.service.HistoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/history/")
public class HistoryController {

    private HistoryService historyService;
    private AnimalService animalService;

    public HistoryController(HistoryService historyService, AnimalService animalService) {
        this.historyService = historyService;
        this.animalService = animalService;
    }

    @GetMapping("getAll")
    public List<History> getAll(@RequestParam int animalId, @RequestParam String dateMin, @RequestParam String dateMax,
            @RequestParam String weightMin, @RequestParam String weightMax, @RequestParam String heightMin,
            @RequestParam String heightMax) throws Exception {
        Animal animal = animalService.getById(animalId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return historyService.getAll(animal, dateMin != null && !dateMin.isEmpty() ? sdf.parse(dateMin) : null,
                dateMax != null && !dateMax.isEmpty() ? sdf.parse(dateMax) : null,
                weightMin != null && !weightMin.isEmpty() ? Double.parseDouble(weightMin) : null,
                weightMax != null && !weightMax.isEmpty() ? Double.parseDouble(weightMax) : null,
                heightMin != null && !heightMin.isEmpty() ? Double.parseDouble(heightMin) : null,
                heightMax != null && !heightMax.isEmpty() ? Double.parseDouble(heightMax) : null);
    }

    @PostMapping("update")
    public void update() {

    }
}
