package com.julio.farmsys.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.julio.farmsys.model.Animal;
import com.julio.farmsys.model.History;
import com.julio.farmsys.reports.HistoryReport;
import com.julio.farmsys.service.AnimalService;
import com.julio.farmsys.service.HistoryService;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void update(@RequestBody String data) throws Exception {
        Map<String, Object> json = new BasicJsonParser().parseMap(data);

        if (json.get("animalId") == null || json.get("animalId").toString().isEmpty()) {
            throw new Exception("Nenhum animal selecionado");
        }

        if (json.get("date") == null || json.get("date").toString().isEmpty()) {
            throw new Exception("Campo data vazio");
        }

        if (json.get("weight") == null || json.get("weight").toString().isEmpty()) {
            throw new Exception("Campo peso vazio");
        }

        if (json.get("height") == null || json.get("height").toString().isEmpty()) {
            throw new Exception("Campo altura vazio");
        }

        History h = new History();

        if (json.get("id") == null || json.get("id").toString().isEmpty()) {
            h.setId(0l);
        }

        else {
            h = historyService.getById(NumberUtils.parseNumber(json.get("id").toString(), Long.class));
        }

        try {
            h.setAnimal(animalService.getById(Integer.parseInt(json.get("animalId").toString())));
        }

        catch (Exception e) {
            throw new Exception("Animal não existe");
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            h.setDate(sdf.parse(json.get("date").toString()));
        }

        catch (Exception e) {
            throw new Exception("Formato de data invalido");
        }

        try {
            h.setWeight(Double.parseDouble(json.get("weight").toString()));
        }

        catch (Exception e) {
            throw new Exception("Valor invalido no campo peso");
        }

        try {
            h.setHeight(Double.parseDouble(json.get("height").toString()));
        }

        catch (Exception e) {
            throw new Exception("Valor invalido no campo altura");
        }

        historyService.save(h);
    }

    @GetMapping(value = "pdf")
    public void pdf(@RequestParam int animalId, @RequestParam String dateMin, @RequestParam String dateMax,
            @RequestParam String weightMin, @RequestParam String weightMax, @RequestParam String heightMin,
            @RequestParam String heightMax, HttpServletResponse response) throws Exception {
        Animal animal = animalService.getById(animalId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HistoryReport report = new HistoryReport(
                historyService.getAll(animal, dateMin != null && !dateMin.isEmpty() ? sdf.parse(dateMin) : null,
                        dateMax != null && !dateMax.isEmpty() ? sdf.parse(dateMax) : null,
                        weightMin != null && !weightMin.isEmpty() ? Double.parseDouble(weightMin) : null,
                        weightMax != null && !weightMax.isEmpty() ? Double.parseDouble(weightMax) : null,
                        heightMin != null && !heightMin.isEmpty() ? Double.parseDouble(heightMin) : null,
                        heightMax != null && !heightMax.isEmpty() ? Double.parseDouble(heightMax) : null));
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=RelatorioAnimais.pdf");
        report.generate(response);
    }

    @PostMapping( value = "delete" )
    public void delete(@RequestParam long id) {
        historyService.delete(historyService.getById(id));
    }
}
