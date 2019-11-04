package hpn.system.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.Donneur;
import hpn.system.beans.Prelevement;
import hpn.system.beans.ResponsableMedical;
import hpn.system.dao.DAODonneur;
import hpn.system.dao.DAOPrelevement;
import hpn.system.dao.DAOResponsableMedical;
import hpn.system.form.FormPrelevement;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/prelevement")
public class ServletPrelevement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String VUE_FORM_AJOUT = "/WEB-INF/add_prelevement.jsp";

	public ServletPrelevement() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("connected") != null) {

			if (request.getParameter("action") != null) {
				if (request.getParameter("action").equals("add")) {
					FormPrelevement formPrelevement = new FormPrelevement(request);
					Prelevement prelevement = formPrelevement.creerPrelevement();

					if (formPrelevement.getErreurs().isEmpty()) {
						DAOPrelevement daoPrelevement = new DAOPrelevement();
						daoPrelevement.insert(prelevement);

						/**
						 * Mise à jour de la date du dernier don chez le donneur concerné
						 */
						DAODonneur daoDonneur = new DAODonneur();
						prelevement.getMyDonneur().setDateDernierDon(prelevement.getDatePrelevement());
						prelevement.getMyDonneur().setNombreDon(prelevement.getMyDonneur().getNombreDon() + 1);
						daoDonneur.update(prelevement.getMyDonneur());

						/**
						 * var, variable envoyé avec la requette elle permet de determiner si sa valeur
						 * vaut "donneur" alors on rédirige vers le detail d'un prelevement 
						 */
						if (request.getParameter("var") != null) {
							if (request.getParameter("var").equals("donneur")) {
								response.sendRedirect(request.getContextPath() + "/list?var=detaildonneur&id="
										+ request.getParameter("id_donneur"));
							}
						} else {
							response.sendRedirect(request.getContextPath() + "/list?var=prelevement");
						}

					} else {

						DAODonneur daoDonneur = new DAODonneur();
						DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

						List<Donneur> donneurs = new ArrayList<Donneur>();
						donneurs = daoDonneur.selectAll();

						List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();
						responsableMedicals = daoResponsableMedical.selectAll();

						request.setAttribute("donneurs", donneurs);
						request.setAttribute("responsables", responsableMedicals);
						request.setAttribute("prelevement", prelevement);
						request.setAttribute("erreurs", formPrelevement.getErreurs());
						request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
					}
				}
			} else {

				DAODonneur daoDonneur = new DAODonneur();
				DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

				List<Donneur> donneurs = new ArrayList<Donneur>();
				donneurs = daoDonneur.selectAll();

				List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();
				responsableMedicals = daoResponsableMedical.selectAll();

				request.setAttribute("donneurs", donneurs);
				request.setAttribute("responsables", responsableMedicals);

				request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
			}

		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
	}
}
