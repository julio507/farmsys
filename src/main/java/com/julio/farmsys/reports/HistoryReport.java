package com.julio.farmsys.reports;

import java.text.SimpleDateFormat;
import java.util.List;

import com.julio.farmsys.model.History;

public class HistoryReport extends Report {

    private List<History> list;

    public HistoryReport(List<History> list) {
        super("Historico");
        this.tableHeaders = new String[] { "ID", "Data", "Peso", "Altura" };
        this.tableWidths = new float[] { 0.1f, 0.3f, 0.3f, 0.3f };
        this.list = list;
    }

    @Override
    void generateData() {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );

        for (History history : list ) {
            table.addCell( String.valueOf( history.getId() ) );
            table.addCell( sdf.format( history.getDate() ) );
            table.addCell( String.valueOf( history.getWeight() ) );
            table.addCell( String.valueOf( history.getHeight() ) );
        }
    }

}
