package com.julio.farmsys.reports;

import java.text.SimpleDateFormat;
import java.util.List;

import com.julio.farmsys.model.Animal;

public class AnimalReport extends Report {

    private List<Animal> animals;

    public AnimalReport(List<Animal> animals) {
        super("Relatorio de Animais");
        this.tableHeaders = new String[]{ "ID", "Identificação", "Especie", "Data de Nacimento", "Data de Aquisição", "Peso", "Altura", "Ativo" };
        this.tableWidths = new float[]{ 0.1f, 0.2f, 0.2f, 0.2f, 0.2f, 0.1f, 0.1f, 0.1f };
        this.animals = animals;
    }

    @Override
    void generateData() {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        
        for (Animal animal : animals) {
            table.addCell( String.valueOf( animal.getId() ) );
            table.addCell( animal.getIdentification() );
            table.addCell( animal.getSpecie() );
            table.addCell( sdf.format( animal.getBornDate() ) );
            table.addCell( sdf.format( animal.getAcquisitionDate() ) );
            table.addCell( String.valueOf( animal.getWeight() ) );
            table.addCell( String.valueOf( animal.getHeight() ) );
            table.addCell( String.valueOf( animal.isActive() ) );
        }
    }
}
