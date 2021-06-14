package com.julio.farmsys.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.reports.AnimalReport;
import com.julio.farmsys.service.AnimalService;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/animal/")
public class AnimalController {

    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("getAll")
    public List<Animal> getAll(@RequestParam String identification, @RequestParam String species, @RequestParam String bornDateMin,
            @RequestParam String bornDateMax, @RequestParam String aquisitionDateMin,
            @RequestParam String aquisitionDateMax, @RequestParam String weightMin, @RequestParam String weightMax,
            @RequestParam String heightMin, @RequestParam String heightMax, @RequestParam String active)
            throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return animalService.getAll(species, identification,
                bornDateMin != null & !bornDateMin.isEmpty() ? sdf.parse(bornDateMin) : null,
                bornDateMax != null && !bornDateMax.isEmpty() ? sdf.parse(bornDateMax) : null,
                aquisitionDateMin != null && !aquisitionDateMin.isEmpty() ? sdf.parse(aquisitionDateMin) : null,
                aquisitionDateMax != null && !aquisitionDateMax.isEmpty() ? sdf.parse(aquisitionDateMax) : null,
                weightMin != null && !weightMin.isEmpty() ? Double.parseDouble(weightMin) : null,
                weightMax != null && !weightMax.isEmpty() ? Double.parseDouble(weightMax) : null,
                heightMin != null && !heightMin.isEmpty() ? Double.parseDouble(heightMin) : null,
                heightMax != null && !heightMax.isEmpty() ? Double.parseDouble(heightMax) : null,
                Boolean.parseBoolean(active));
    }

    @PostMapping("update")
    public void update(@RequestBody String data) throws Exception {

        Map<String, Object> json = new BasicJsonParser().parseMap(data);

        if (json.get("identification") == null || json.get("identification").toString().isEmpty()) {
            throw new Exception("Campo Identificação vazio");
        }

        if (json.get("specie") == null || json.get("specie").toString().isEmpty()) {
            throw new Exception("Campo Espécie vazio");
        }

        if (json.get("bornDate") == null || json.get("bornDate").toString().isEmpty()) {
            throw new Exception("Campo Data de Nascimento vazio");
        }

        if (json.get("acquisitionDate") == null || json.get("acquisitionDate").toString().isEmpty()) {
            throw new Exception("Campo Data de Aquisição vazio");
        }

        Animal a = new Animal();

        if (json.get("id") == null || json.get("id").toString().isEmpty()) {
            a.setId(0l);
        }

        else {
            a = animalService.getById(NumberUtils.parseNumber(json.get("id").toString(), Long.class));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        a.setIdentification(json.get("identification").toString());

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

        a.setActive(Boolean.valueOf(json.get("active").toString()));

        animalService.save(a);
    }

    @GetMapping(value = "pdf")
    public void pdf( @RequestParam String identification, @RequestParam String species, @RequestParam String bornDateMin, @RequestParam String bornDateMax,
            @RequestParam String aquisitionDateMin, @RequestParam String aquisitionDateMax,
            @RequestParam String weightMin, @RequestParam String weightMax, @RequestParam String heightMin,
            @RequestParam String heightMax, @RequestParam String active, HttpServletResponse response)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        AnimalReport report = new AnimalReport(animalService.getAll( identification, species,
                bornDateMin != null & !bornDateMin.isEmpty() ? sdf.parse(bornDateMin) : null,
                bornDateMax != null && !bornDateMax.isEmpty() ? sdf.parse(bornDateMax) : null,
                aquisitionDateMin != null && !aquisitionDateMin.isEmpty() ? sdf.parse(aquisitionDateMin) : null,
                aquisitionDateMax != null && !aquisitionDateMax.isEmpty() ? sdf.parse(aquisitionDateMax) : null,
                weightMin != null && !weightMin.isEmpty() ? Double.parseDouble(weightMin) : null,
                weightMax != null && !weightMax.isEmpty() ? Double.parseDouble(weightMax) : null,
                heightMin != null && !heightMin.isEmpty() ? Double.parseDouble(heightMin) : null,
                heightMax != null && !heightMax.isEmpty() ? Double.parseDouble(heightMax) : null,
                Boolean.parseBoolean(active)));
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=RelatorioAnimais.pdf");
        report.generate(response);
    }

}
