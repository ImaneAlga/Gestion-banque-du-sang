package hpn.system.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import hpn.system.beans.Donneur;
import hpn.system.beans.Prelevement;
import hpn.system.dao.DAODonneur;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/pdfdonneur")
public class DonneurPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DonneurPDF() {
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
		Donneur donneur = new Donneur();

		if (request.getParameter("id") != null) {
			donneur = daoDonneur.selectOne(Integer.parseInt(request.getParameter("id")));

			OutputStream stream = response.getOutputStream();

			try {

				Document document = new Document();
				PdfWriter.getInstance(document, stream);
				document.open();

				document.addTitle("Fiche du donneur");

				Image image = Image.getInstance("/home/alga/eclipse-workspace/BloodBankSystem/logo-dark.jpeg");
				image.setAlignment(Element.ALIGN_LEFT);
				image.scaleToFit(50, 50);
				document.add(image);

				Paragraph paragraph = new Paragraph();
				Font font = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);

				paragraph.add(new Phrase("FICHE DU DONNEUR, CNI : " + donneur.getNumeroCNI(), font));
				paragraph.setAlignment(Element.ALIGN_CENTER);

				paragraph.setSpacingAfter(10);
				document.add(paragraph);

				Paragraph paragraph1 = new Paragraph();
				Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);

				paragraph1.setFont(font1);

				paragraph1.add(new Phrase("Nom :  " + donneur.getNom()));
				paragraph1.add(new Phrase(Chunk.NEWLINE));
				paragraph1.add(new Phrase(Chunk.NEWLINE));

				if (donneur.getPrenom() != null) {
					paragraph1.add(new Phrase("Prénom :  " + donneur.getPrenom()));
				}
				paragraph1.add(new Phrase(Chunk.NEWLINE));
				paragraph1.add(new Phrase(Chunk.NEWLINE));

				if (donneur.getDateNaiss() != null) {
					paragraph1.add(new Phrase("Date de naissance :  " + donneur.getDateNaiss().toString()));
					paragraph1.add(new Phrase(Chunk.NEWLINE));
					paragraph1.add(new Phrase(Chunk.NEWLINE));
				}

				paragraph1.add(new Phrase("Sexe :  " + donneur.getSexe()));
				paragraph1.add(new Phrase(Chunk.NEWLINE));
				paragraph1.add(new Phrase(Chunk.NEWLINE));

				paragraph1.add(new Phrase("Adresse :  " + donneur.getAdresse()));
				paragraph1.add(new Phrase(Chunk.NEWLINE));
				paragraph1.add(new Phrase(Chunk.NEWLINE));

				if (donneur.getFacteurRhesus().equals("RH+")) {
					paragraph1.add(new Phrase("Groupe Sanguin :  " + donneur.getGroupeSanguin() + "+"));
				}
				if (donneur.getFacteurRhesus().equals("RH-")) {
					paragraph1.add(new Phrase("Groupe Sanguin :  " + donneur.getGroupeSanguin() + "-"));
				}
				paragraph1.add(new Phrase(Chunk.NEWLINE));
				paragraph1.add(new Phrase(Chunk.NEWLINE));

				if (donneur.getStatutMatrimonial() != null) {
					paragraph1.add(new Phrase("Statut Matrimonial :  " + donneur.getStatutMatrimonial()));
				}

				paragraph1.setAlignment(Element.ALIGN_LEFT);
				document.add(paragraph1);

				/*
				 * Nouveau tableau - 5 colonnes avec les largeurs respectivement 1/4/3/1/1/
				 */
				PdfPTable table = new PdfPTable(new float[] { 3f, 5f, 4f, 3f, 3f });
				table.setWidthPercentage(98);

				/* Ajout des entêtes du tableau */
				table.setSpacingAfter(20);

				Font headingStyle = new Font(Font.FontFamily.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);
				table.addCell(new Phrase("Ordre", headingStyle));
				table.addCell(new Phrase("Date", headingStyle));
				table.addCell(new Phrase("Volume(ml)", headingStyle));
				table.addCell(new Phrase("T.A", headingStyle));
				table.addCell(new Phrase("Poids(Kg)", headingStyle));

				if (!donneur.getMyPrelevements().isEmpty()) {
					Paragraph paragraph2 = new Paragraph();
					Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.BLACK);

					paragraph2.add(new Phrase("LES DONS ENREGISTRES", font2));
					paragraph2.setAlignment(Element.ALIGN_CENTER);
					paragraph2.setSpacingAfter(20);

					document.add(paragraph2);
					int i = 0;

					for (Prelevement prelevement : donneur.getMyPrelevements()) {
						table.addCell(String.valueOf(++i));
						table.addCell(prelevement.getDatePrelevement().toString());
						table.addCell(String.valueOf(prelevement.getQuantite()));
						table.addCell(String.valueOf(prelevement.getTensionDonneur()));
						table.addCell(String.valueOf(prelevement.getPoidsDonneur()));

					}

				} else {
					Paragraph paragraph2 = new Paragraph();
					Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.BLACK);

					paragraph2.add(new Phrase("ACUN DON ENREGISTRES", font2));
					paragraph2.setAlignment(Element.ALIGN_CENTER);
					paragraph2.setSpacingAfter(20);

					document.add(paragraph2);
				}

				table.setSpacingAfter(25);
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

}
