package hpn.system.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hpn.system.beans.Donneur;
import hpn.system.beans.GroupeExamen;
import hpn.system.beans.Poche;
import hpn.system.beans.Prelevement;
import hpn.system.beans.Receveur;
import hpn.system.beans.ResponsableMedical;
import hpn.system.beans.Transfert;
import hpn.system.beans.TypeExamen;
import hpn.system.dao.DAODonneur;
import hpn.system.dao.DAOGroupeExamen;
import hpn.system.dao.DAOPoche;
import hpn.system.dao.DAOPrelevement;
import hpn.system.dao.DAOReceveur;
import hpn.system.dao.DAOResponsableMedical;
import hpn.system.dao.DAOTranfert;
import hpn.system.dao.DAOTypeExamen;

/**
 * 
 * @author alga 
 * Servlet permettant l'affichage des donnees recuperer dans la
 *         base des donnees
 */
@WebServlet("/list")
public class ServletAffichage extends HttpServlet {	

	private static final long serialVersionUID = 1L;
	private static final String VUE_RESPONSABLES = "/WEB-INF/all_responsable.jsp";
	private static final String VUE_DONNEUR = "/WEB-INF/all_donneur.jsp";
	private static final String VUE_GROUPE = "/WEB-INF/all_groupe_exam.jsp";
	private static final String VUE_TYPE = "/WEB-INF/all_type_exam.jsp";
	private static final String VUE_PRELEVEMENT = "/WEB-INF/all_prelevement.jsp";
	private static final String VUE_UN_PRELEVEMENT = "/WEB-INF/detail_prelevement.jsp";
	private static final String VUE_RESPONSABLE = "/WEB-INF/detail_responsable.jsp";
	private static final String VUE_DETAIL_DONNEUR = "/WEB-INF/detail_donneur.jsp";
	private static final String VUE_POCHE = "/WEB-INF/all_poche.jsp";
	private static final String ATT_LIST_POCHES = "poches";
	private static final String VUE_POCHE_LIVREE = "poches";
	private static final String VUE_RECEVEUR = "/WEB-INF/all_receveur.jsp";
	private static final String VUE_TRANSFERT = "/WEB-INF/all_transfert.jsp";
	private static final Object ATT_POCHE_STOCK = "stock";
	private static final Object ATT_POCHE_SORTIE = "sortie";
	private static final Object ATT_POCHE_INVD = "invalide";
	private static final String INDEXE = "/WEB-INF/index.jsp";

	public ServletAffichage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		if (request.getSession().getAttribute("connected") != null) {


			/**
			 * Controle Notifications
			 */

			DAOPoche daoP = new DAOPoche();
			List<Poche> ps = new ArrayList<Poche>();
			ps = daoP.selectAllInvalide();

			HttpSession session = request.getSession();

			session.setAttribute(ATT_LIST_POCHES, ps);

			/**
			 * Afficher la liste poches
			 */
			if (request.getParameter("var").equals("poche")) {
				DAOPoche daoPoche = new DAOPoche();
				List<Poche> poches = new ArrayList<Poche>();

				poches = daoPoche.selectAll();
				request.setAttribute("poches", poches);
				request.setAttribute("var", ATT_POCHE_STOCK);
				request.getServletContext().getRequestDispatcher(VUE_POCHE).forward(request, response);
			}

			/**
			 * Afficher la liste poches transferées
			 */
			if (request.getParameter("var").equals("poche_livree")) {
				DAOPoche daoPoche = new DAOPoche();
				List<Poche> poches = new ArrayList<Poche>();

				poches = daoPoche.selectAllTransferer();
				request.setAttribute("poches", poches);
				request.setAttribute("var", ATT_POCHE_SORTIE);
				request.getServletContext().getRequestDispatcher(VUE_POCHE).forward(request, response);
			}

			/**
			 * Afficher la liste poches invalide
			 */
			if (request.getParameter("var").equals("poche_invalide")) {
				DAOPoche daoPoche = new DAOPoche();
				List<Poche> poches = new ArrayList<Poche>();

				poches = daoPoche.selectAllInvalide();
				request.setAttribute("poches", poches);
				request.setAttribute("var", ATT_POCHE_INVD);
				request.getServletContext().getRequestDispatcher(VUE_POCHE).forward(request, response);
			}

			/**
			 * Renvoi page d'acceuil
			 */
			if (request.getParameter("var").equals("index")) {
				request.getServletContext().getRequestDispatcher(INDEXE).forward(request, response);
			}

			/**
			 * Affichage des donneurs
			 */
			if (request.getParameter("var").equals("donneur")) {
				DAODonneur daoDonneur = new DAODonneur();
				List<Donneur> donneurs = new ArrayList<Donneur>();

				donneurs = daoDonneur.selectAll();
				request.setAttribute("donneurs", donneurs);
				request.getServletContext().getRequestDispatcher(VUE_DONNEUR).forward(request, response);
			}

			/**
			 * Detail Sur Un Donneur
			 */
			if (request.getParameter("var").equals("detaildonneur")) {
				if (request.getParameter("id") != null) {
					DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
					List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();

					responsableMedicals = daoResponsableMedical.selectAll();

					DAODonneur daoDonneur = new DAODonneur();
					Donneur donneur = new Donneur();

					donneur = daoDonneur.selectOne(Integer.parseInt(request.getParameter("id")));
					request.setAttribute("donneur", donneur);
					request.setAttribute("responsables", responsableMedicals);
					request.getServletContext().getRequestDispatcher(VUE_DETAIL_DONNEUR).forward(request, response);
				}
			}

			/**
			 * Affichage des groupes d'examen
			 */
			if (request.getParameter("var").equals("groupe")) {
				DAOGroupeExamen daoGroupeExamen = new DAOGroupeExamen();
				List<GroupeExamen> groupeExamens = new ArrayList<GroupeExamen>();

				groupeExamens = daoGroupeExamen.selectAll();
				request.setAttribute("groupes", groupeExamens);
				request.getServletContext().getRequestDispatcher(VUE_GROUPE).forward(request, response);
			}

			/**
			 * Affichage des types d'examen
			 */
			if (request.getParameter("var").equals("type")) {

				DAOGroupeExamen daoGroupeExamen = new DAOGroupeExamen();
				List<GroupeExamen> groupeExamens = new ArrayList<GroupeExamen>();

				groupeExamens = daoGroupeExamen.selectAll();

				DAOTypeExamen daoTypeExamen = new DAOTypeExamen();
				List<TypeExamen> typeExamens = new ArrayList<TypeExamen>();

				typeExamens = daoTypeExamen.selectAll();

				request.setAttribute("types", typeExamens);
				request.setAttribute("groupes", groupeExamens);

				request.getServletContext().getRequestDispatcher(VUE_TYPE).forward(request, response);
			}

			/**
			 * Affichage de prelevements
			 */
			if (request.getParameter("var").equals("prelevement")) {

				DAODonneur daoDonneur = new DAODonneur();
				DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
				DAOPrelevement daoPrelevement = new DAOPrelevement();

				List<Donneur> donneurs = new ArrayList<Donneur>();
				donneurs = daoDonneur.selectAll();

				List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();
				responsableMedicals = daoResponsableMedical.selectAll();

				List<Prelevement> prelevements = new ArrayList<Prelevement>();
				prelevements = daoPrelevement.selectAll();

				request.setAttribute("donneurs", donneurs);
				request.setAttribute("responsables", responsableMedicals);
				request.setAttribute("prelevements", prelevements);

				request.getServletContext().getRequestDispatcher(VUE_PRELEVEMENT).forward(request, response);
			}

			/**
			 * Detail d'un prelevement
			 */
			if (request.getParameter("var").equals("unprelevement")) {
				DAOPrelevement daoPrelevement = new DAOPrelevement();
				DAOTypeExamen daoTypeExamen = new DAOTypeExamen();
				// DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

				List<TypeExamen> typeExamens = new ArrayList<TypeExamen>();
				typeExamens = daoTypeExamen.selectAll();

				Prelevement prelevement = new Prelevement();
				prelevement = daoPrelevement.selectOne(Integer.parseInt(request.getParameter("id")));

				request.setAttribute("prelevement", prelevement);
				request.setAttribute("types", typeExamens);

				request.getServletContext().getRequestDispatcher(VUE_UN_PRELEVEMENT).forward(request, response);
			}

			/**
			 * Detail d'un prelevement apres examen
			 */
			if (request.getParameter("var").equals("examen_fait")) {
				DAOPrelevement daoPrelevement = new DAOPrelevement();
				DAOTypeExamen daoTypeExamen = new DAOTypeExamen();
				// DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

				List<TypeExamen> typeExamens = new ArrayList<TypeExamen>();
				typeExamens = daoTypeExamen.selectAll();

				Prelevement prelevement = new Prelevement();
				prelevement = daoPrelevement.selectOne(Integer.parseInt(String.valueOf(request.getAttribute("id"))));

				request.setAttribute("prelevement", prelevement);
				request.setAttribute("types", typeExamens);

				request.getServletContext().getRequestDispatcher(VUE_UN_PRELEVEMENT).forward(request, response);
			}

			/**
			 * Affrichage des Personnels Medicaux
			 */
			if (request.getParameter("var").equals("responsables")) {

				DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
				List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();

				responsableMedicals = daoResponsableMedical.selectAll();

				request.setAttribute("responsables", responsableMedicals);

				request.getServletContext().getRequestDispatcher(VUE_RESPONSABLES).forward(request, response);
			}

			/**
			 * Detail sur Un Personnel Medical
			 */
			if (request.getParameter("var").equals("responsable")) {

				if (request.getParameter("id") != null) {
					DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
					ResponsableMedical responsableMedical = new ResponsableMedical();

					String creerUnCompte = "non";
					
					responsableMedical = daoResponsableMedical.selectOne(Integer.parseInt(request.getParameter("id")));

					if (responsableMedical.getMyCompte() == null) {
						creerUnCompte = "oui";
					}
					
					request.setAttribute("responsable", responsableMedical);
					request.setAttribute("new_compte", creerUnCompte);

					request.getServletContext().getRequestDispatcher(VUE_RESPONSABLE).forward(request, response);
				}
			}

			/**
			 * Afficher la liste des receveur
			 */

			if (request.getParameter("var").equals("receveur")) {
				DAOReceveur daoReceveur = new DAOReceveur();
				List<Receveur> receveurs = new ArrayList<Receveur>();
				receveurs = daoReceveur.selectAll();
				request.setAttribute("receveurs", receveurs);
				request.getServletContext().getRequestDispatcher(VUE_RECEVEUR).forward(request, response);
			}

			/**
			 * Afficher liste des transferts
			 */
			if (request.getParameter("var").equals("transfert")) {
				DAOTranfert daoTransfert = new DAOTranfert();
				List<Transfert> transferts = new ArrayList<Transfert>();
				transferts = daoTransfert.selectAll();
				request.setAttribute("transferts", transferts);
				request.getServletContext().getRequestDispatcher(VUE_TRANSFERT).forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
	}
}
