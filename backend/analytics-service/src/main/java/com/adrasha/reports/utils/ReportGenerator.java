package com.adrasha.reports.utils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

 

public class ReportGenerator implements AutoCloseable{

	private final String REPORT_TITLE = "Report Title";
	private final String NEW_LINE = "\n";
	private final String NO_DATA_AVAILABLE = "No Data Available";
	private final int TITLE_FONT_SIZE = 16;
//	private final int HEADER_FOOTER_FONT_SIZE = 12;
	
	private final Document document;
	private final ByteArrayOutputStream stream;
	private final PdfWriter writer;
	
	
	private ReportGenerator() {
		try {
		this.stream = new ByteArrayOutputStream();
		this.document = new Document();
		this.writer = PdfWriter.getInstance(document, stream);
		
		this.document.open();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize PDF document");
		}
	}
	
	public static ReportGenerator createDocument() {
		return new ReportGenerator();
	}
	
	private void addElement(Element element) {
        try {
            addElement(element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add element to PDF document", e);
        }
    }

	public ReportGenerator addTitle(String titleText) {
		String text = titleText == null || titleText.isBlank() ? REPORT_TITLE : titleText;
		
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, TITLE_FONT_SIZE);
		Paragraph title = new Paragraph(text, titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		
		addElement(title);
		addElement(new Paragraph(NEW_LINE));
		return this;
	}
	
//	private void addParagraphToDocument(List<Paragraph> list) {
//		list.forEach(paragraph->{
//			addElement(paragraph);
//		});
//		
//		addElement(new Paragraph(NEW_LINE));
//	}
//	
//	private Paragraph addLine(List<Phrase> phrases) {
//		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, HEADER_FOOTER_FONT_SIZE);
//		
//		Paragraph line = new Paragraph();
//		phrases.forEach(phrase->{
//			phrase.setFont(headerFont);
//			line.add(phrase);
//		});
//		line.setAlignment(Element.ALIGN_LEFT);
//		return line;
//	}
//	
	
	// TODO : add Header
	
	// TODO : add Footer
	public ReportGenerator addFooter() {
	    writer.setPageEvent(new PdfPageEventHelper() {
	        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);
	        String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

	        @Override
	        public void onEndPage(PdfWriter writer, Document document) {
	            Rectangle rect = document.getPageSize();
	            PdfContentByte cb = writer.getDirectContent();

	            // Footer separator line
	            cb.setLineWidth(0.5f);
	            cb.moveTo(rect.getLeft() + 36, rect.getBottom() + 40);
	            cb.lineTo(rect.getRight() - 36, rect.getBottom() + 40);
	            cb.stroke();

	            // Application name (left-aligned)
	            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
	                    new Phrase("Generated at : "+ createdDate, footerFont),
	                    rect.getLeft() + 36, rect.getBottom() + 25, 0);

	            // Page number (right-aligned)
	            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
	                    new Phrase("Page no.: " + writer.getPageNumber(), footerFont),
	                    rect.getRight() - 36, rect.getBottom() + 25, 0);
	        }
	    });
	    return this;
	}

	
	public ReportGenerator addTable(PdfPTable table) {
		if(table == null) {
			addElement(new Paragraph(NO_DATA_AVAILABLE));
		}
		
		addElement(table);
		addElement(new Paragraph(NEW_LINE));
		return this;
	}

    public byte[] closeAndGetPdf() {
        try {
            if (document.isOpen()) {
                document.close();
            }
            return stream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to finalize PDF document", e);
        }
    }
	
	  @Override
	    public void close() {
	        if (document.isOpen()) {
	            document.close();
	        }
	    }
}