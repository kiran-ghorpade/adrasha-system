package com.adrasha.analytics.reports.utils;

import java.util.List;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class ReportTableGenerator {

    private static final int HEADER_FONT_SIZE = 12;
    private static final int DATA_FONT_SIZE = 10;

    private PdfPTable table;

    private ReportTableGenerator(int columnCount) {
        this.table = new PdfPTable(columnCount);
        this.table.setWidthPercentage(100);
    }

    public static ReportTableGenerator create(int columnCount) {
        return new ReportTableGenerator(columnCount);
    }

    public ReportTableGenerator addHeaders(List<String> headers) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FONT_SIZE);

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        return this;
    }

    public ReportTableGenerator addData(List<List<Object>> rows) {
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, DATA_FONT_SIZE);

        for (List<Object> row : rows) {
            for (Object value : row) {
                String text = value != null ? value.toString() : "";
                PdfPCell cell = new PdfPCell(new Phrase(text, dataFont));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }
        }

        return this;
    }

    public PdfPTable build() {
        return table;
    }
}
