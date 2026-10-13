package de.praxis.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import de.praxis.model.Arzt;
import de.praxis.model.Patient;

import java.io.FileOutputStream;
import java.util.List;

public class ArztPdfService {
    public static void createArztReport(
            List<Arzt> arzts, String filePath) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Ärztebericht", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            Paragraph anzahl = new Paragraph("Anzahl der Ärzte: " + arzts.size());
            document.add(anzahl);
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Spezialisierung");
            table.addCell("telefonnummer");

            for (Arzt arzt : arzts) {
                table.addCell(
                        String.valueOf(arzt.getArzt_id())
                );
                table.addCell(arzt.getName());
                table.addCell(arzt.getSpezialisierung());

                table.addCell(arzt.getTelefon());

            }
            document.add(table);
            document.close();
            System.out.println("PDF erfolgreich erstellt: " + filePath);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
