package de.praxis.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import de.praxis.model.Patient;

import java.io.FileOutputStream;
import java.util.List;

public class PatientPdfService {

    public static void createPatientReport(
            List<Patient> patients, String filePath) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Patientenbericht", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            Paragraph anzahl = new Paragraph("Anzahl der Patienten: " + patients.size());
            document.add(anzahl);
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Alter");
            table.addCell("Geschlecht");
            table.addCell("Abteilung");
            table.addCell("Telefon");
            table.addCell("E-Mail");
            for (Patient patient : patients) {
                table.addCell(
                        String.valueOf(patient.getPatient_id())
                );
                table.addCell(patient.getName());
                table.addCell(String.valueOf(patient.getPatient_alter()));

                table.addCell(patient.getGeschlecht());

                table.addCell(patient.getAbteilung());

                table.addCell(patient.getTelefonnummer());

                table.addCell(patient.getEmail());
            }
            document.add(table);
            document.close();
            System.out.println("PDF erfolgreich erstellt: " + filePath);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}