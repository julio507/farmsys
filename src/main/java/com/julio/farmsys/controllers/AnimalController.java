package com.julio.farmsys.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.service.AnimalService;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/animal/")
public class AnimalController {

    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("getAll")
    public List<Animal> getAll() {
        return animalService.getAll();
    }

    @PostMapping("update")
    public void update(@RequestBody String data) throws Exception {

        Map<String, Object> json = new BasicJsonParser().parseMap(data);

        if (json.get("specie") == null || json.get("specie").toString().isEmpty()) {
            throw new Exception("Campo Espécie vazio");
        }

        if (json.get("bornDate") == null || json.get("bornDate").toString().isEmpty()) {
            throw new Exception("Campo Data de Nascimento vazio");
        }

        if (json.get("acquisitionDate") == null || json.get("acquisitionDate").toString().isEmpty()) {
            throw new Exception("Campo Data de Aquisição vazio");
        }

        if (json.get("weight") == null || json.get("weight").toString().isEmpty()) {
            throw new Exception("Campo Peso vazio");
        }

        if (json.get("height") == null || json.get("height").toString().isEmpty()) {
            throw new Exception("Campo Altura vazio");
        }

        Animal a = new Animal();

        if (json.get("id") == null || json.get("id").toString().isEmpty()) {
            a.setId(0l);
        }

        else {
            a = animalService.getById(NumberUtils.parseNumber(json.get("id").toString(), Long.class));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        a.setSpecie(json.get("specie").toString());

        try {
            a.setBornDate(sdf.parse(json.get("bornDate").toString()));
        }

        catch (Exception e) {
            throw new Exception("Formato de data invalido no campo Data de Nacimeto");
        }

        try {
            a.setAcquisitionDate(sdf.parse(json.get("acquisitionDate").toString()));
        }

        catch (Exception e) {
            throw new Exception("Formato de data invalido no campo Data de Aquisição");
        }

        try {
            a.setWeight(NumberUtils.parseNumber(json.get("weight").toString(), Double.class));
        }

        catch (Exception e) {
            throw new Exception("Valor invalido no campo Peso");
        }

        try {
            a.setHeight(NumberUtils.parseNumber(json.get("height").toString(), Double.class));
        }

        catch (Exception e) {
            throw new Exception("Valor invalido no campo Altura");
        }

        animalService.save(a);
    }
}
