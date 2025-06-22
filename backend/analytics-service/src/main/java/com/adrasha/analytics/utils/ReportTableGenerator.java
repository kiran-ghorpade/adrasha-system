package com.adrasha.analytics.utils;

import java.util.List;
import java.util.Map;

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
	private List<String> fields;

	public static ReportTableGenerator createTable() {
		return new ReportTableGenerator();
	}

	public PdfPTable build() {
		return this.table;
	}

	public ReportTableGenerator addHeaders(List<String> tableHeader) {
		int headerCount = tableHeader.size();

		PdfPTable table = new PdfPTable(headerCount);
		table.setWidthPercentage(100);

		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FONT_SIZE);

		tableHeader.forEach(header -> {
			PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		});

		fields = tableHeader;
		return this;
	}

	public ReportTableGenerator addData(List<Map<String, Object>> data) {

		// check if data empty
		if (data.isEmpty()) {
			return null;
		}

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, DATA_FONT_SIZE);
		data.forEach(row -> {
			fields.forEach(field -> {
				Object value = row.get(field);
				String text = value != null ? value.toString() : "";
				PdfPCell cell = new PdfPCell(new Phrase(text, dataFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			});
		});

		return this;
	}

}
