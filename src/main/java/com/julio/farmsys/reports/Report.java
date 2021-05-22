package com.julio.farmsys.reports;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public abstract class Report {

    protected String title;

    protected String[] tableHeaders = new String[]{};
    protected float[] tableWidths = new float[]{};

    protected Document document = new Document();
    protected PdfPTable table;

    public Report(String title) {
        this.title = title;
    }

    protected void generateTable()
    {
        table = new PdfPTable( tableHeaders.length );
        table.setWidthPercentage(100f);
        table.setWidths(tableWidths);
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
         
        for (String header : tableHeaders) {
            cell.setPhrase(new Phrase(header));
         
            table.addCell(cell);
        }
    }

    public void generate(HttpServletResponse response) {
        try {

            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            Paragraph p = new Paragraph(title);
            p.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(p);

            generateTable();
            generateData();

            document.add( table );

        } catch (Exception e) {
            e.printStackTrace();
        }

        document.close();
    }

    abstract void generateData();
}
