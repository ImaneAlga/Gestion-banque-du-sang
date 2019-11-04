package hpn.system.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import hpn.system.beans.Donneur;
import hpn.system.dao.DAODonneur;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/pdfalldonneurs")
public class DonneursPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DonneursPDF() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=fiche_donneur.pdf");

		DAODonneur daoDonneur = new DAODonneur();
		List<Donneur> donneurs = new ArrayList<Donneur>();

		donneurs = daoDonneur.selectAll();

		OutputStream stream = response.getOutputStream();

		try {

			Document document = new Document();
			PdfWriter.getInstance(document, stream);
			document.open();

			document.addTitle("Liste donneurs");

			Image image = Image.getInstance("/home/alga/eclipse-workspace/BloodBankSystem/logo-dark.jpeg");
			image.setAlignment(Element.ALIGN_LEFT);
			image.scaleToFit(50, 50);
			
			document.add(image);

			Paragraph paragraph = new Paragraph();
			Font font = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);

			paragraph.setSpacingAfter(50);
			paragraph.add(new Phrase("LISTES DES DONNEURS", font));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			
			document.add(paragraph);

			/*
			 * Nouveau tableau - 5 colonnes avec les largeurs respectivement 1/4/3/1/1/
			 */
			PdfPTable table = new PdfPTable(new float[] { 3f, 3f, 2f, 4f, 3f, 2f, 4f });
			table.setWidthPercentage(99);

			/* Ajout des entêtes du tableau */
			

			Font headingStyle = new Font(Font.FontFamily.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);
			table.addCell(new Phrase("Nom", headingStyle));
			table.addCell(new Phrase("Sexe", headingStyle));
			table.addCell(new Phrase("GS", headingStyle));
			table.addCell(new Phrase("Adresse", headingStyle));
			table.addCell(new Phrase("Téléphone", headingStyle));
			table.addCell(new Phrase("Nb. dons", headingStyle));
			table.addCell(new Phrase("Dernier don", headingStyle));

			if (!donneurs.isEmpty()) {

				for (Donneur donneur : donneurs) {
					table.addCell(donneur.getNom());
					table.addCell(donneur.getSexe());
					if (donneur.getFacteurRhesus().equals("RH+")) {
						table.addCell(donneur.getGroupeSanguin() + "+");
					}
					if (donneur.getFacteurRhesus().equals("RH-")) {
						table.addCell(donneur.getGroupeSanguin() + "-");
					}
					table.addCell(donneur.getAdresse());
					table.addCell(donneur.getTelephone());
					table.addCell(String.valueOf(donneur.getNombreDon()));
					if (donneur.getDateDernierDon() != null) {
						table.addCell(donneur.getDateDernierDon().toString());
					}else {
						table.addCell("-");
					}

				}

			} else {
				Paragraph paragraph2 = new Paragraph();
				Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.BLACK);

				paragraph2.add(new Phrase("ACUN DON ENREGISTRES", font2));
				paragraph2.setAlignment(Element.ALIGN_CENTER);
				paragraph2.setSpacingAfter(20);

				document.add(paragraph2);
			}

			table.setSpacingAfter(50);
			document.add(table);

			Paragraph signatureDate = new Paragraph();
			signatureDate = new Paragraph(new Date().toString(), new Font(Font.FontFamily.HELVETICA, 10));
			signatureDate.setAlignment(Element.ALIGN_RIGHT);
			signatureDate.setSpacingAfter(25);
			document.add(signatureDate);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stream.close();
		}

	}

}
