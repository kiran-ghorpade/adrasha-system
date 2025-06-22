package com.adrasha.analytics.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ReportGenerator {

	private final String REPORT_TITLE = "Report Title";
	private final String NEW_LINE = "\n";
	private final String NO_DATA_AVAILABLE = "No Data Available";
	private final int TITLE_FONT_SIZE = 16;
	private final int HEADER_FOOTER_FONT_SIZE = 12;
	
	private static Document document;
	private static ByteArrayOutputStream stream;
	
	public static ReportGenerator createDocument() {
		document = new Document();
		stream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, stream);
		document.open();
		
		return new ReportGenerator();
	}

	public ReportGenerator build() {
		return this;
	}

	public ReportGenerator addTitle(String titleText) {
		String text = titleText.isBlank() ? REPORT_TITLE : titleText;
		
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, TITLE_FONT_SIZE);
		Paragraph title = new Paragraph(text, titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		
		document.add(title);
		document.add(new Paragraph(NEW_LINE));
		return this;
	}
	
	private void addParagraphToDocument(List<Paragraph> list) {
		list.forEach(paragraph->{
			document.add(paragraph);
		});
		
		document.add(new Paragraph(NEW_LINE));
	}
	
	private Paragraph addLine(List<Phrase> phrases) {
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FOOTER_FONT_SIZE);
		
		Paragraph line = new Paragraph();
		phrases.forEach(phrase->{
			phrase.setFont(headerFont);
			line.add(phrase);
		});
		line.setAlignment(Element.ALIGN_LEFT);
		return line;
	}
	
	public ReportGenerator addDefaultHeader() {
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FOOTER_FONT_SIZE);
		
		List<Paragraph> header = new ArrayList<>();
		
		Paragraph line1 = this.addLine(List.of(
							new Phrase("ASHA Name : ", headerFont)
						));
		
		header.add(line1);
		this.addParagraphToDocument(header);
		return this;
	}
	
	public ReportGenerator addDefaultFooter() {
		Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FOOTER_FONT_SIZE);
		
		List<Paragraph> footer = new ArrayList<>();
		
		Paragraph line1 = this.addLine(List.of(
							new Phrase("ASHA Name : ", footerFont)
						));
		
		footer.add(line1);
		this.addParagraphToDocument(footer);
		return this;
	}
	
	public ReportGenerator addFooter(List<Phrase> phrases) {
		Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FOOTER_FONT_SIZE);
		
		Paragraph footer = new Paragraph();
		phrases.forEach(phrase->{
			phrase.setFont(footerFont);
			footer.add(phrase);
		});
		footer.setAlignment(Element.ALIGN_CENTER);

		return this;
	}
	
	public ReportGenerator addTable(PdfPTable table) {
		if(table == null) {
			document.add(new Paragraph(NO_DATA_AVAILABLE));
		}
		
		document.add(table);
		document.add(new Paragraph(NEW_LINE));
		return this;
	}

	public String generatedPdf() {
			document.close();
			return convertToBase64(stream);
	}

	private String convertToBase64(ByteArrayOutputStream stream) {
		return Base64.getEncoder().encodeToString(stream.toByteArray());
	}

}
