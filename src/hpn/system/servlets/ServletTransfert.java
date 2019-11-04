package hpn.system.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.Poche;
import hpn.system.beans.Receveur;
import hpn.system.beans.ResponsableMedical;
import hpn.system.beans.Transfert;
import hpn.system.dao.DAOPoche;
import hpn.system.dao.DAOReceveur;
import hpn.system.dao.DAOResponsableMedical;
import hpn.system.dao.DAOTranfert;
import hpn.system.form.FormTransfert;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/transfert")
public class ServletTransfert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_FORM_AJOUT = "/WEB-INF/add_transfert.jsp";

	public ServletTransfert() {
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
					FormTransfert formTransfert = new FormTransfert(request);
					Transfert transfert = formTransfert.creerTransfert();

					if (formTransfert.getErreurs().isEmpty()) {
						DAOTranfert daoTranfert = new DAOTranfert();
						DAOPoche daoPoche = new DAOPoche();

						daoTranfert.insert(transfert);
						daoPoche.transferer(transfert.getMyPoche());

						response.sendRedirect(request.getContextPath() + "/list?var=transfert");

					} else {

						DAOReceveur daoReceveur = new DAOReceveur();
						DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
						DAOPoche daoPoche = new DAOPoche();

						List<Receveur> receveurs = new ArrayList<Receveur>();
						receveurs = daoReceveur.selectAll();

						List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();
						responsableMedicals = daoResponsableMedical.selectAll();

						List<Poche> poches = new ArrayList<Poche>();
						poches = daoPoche.selectAll();

						request.setAttribute("poches", poches);
						request.setAttribute("receveurs", receveurs);
						request.setAttribute("responsables", responsableMedicals);
						request.setAttribute("erreurs", formTransfert.getErreurs());
						request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
					}
				}
			} else {

				DAOPoche daoPoche = new DAOPoche();
				DAOReceveur daoReceveur = new DAOReceveur();
				DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

				List<Receveur> receveurs = new ArrayList<Receveur>();
				receveurs = daoReceveur.selectAll();

				List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();
				responsableMedicals = daoResponsableMedical.selectAll();

				List<Poche> poches = new ArrayList<Poche>();
				poches = daoPoche.selectAll();

				request.setAttribute("receveurs", receveurs);
				request.setAttribute("responsables", responsableMedicals);
				request.setAttribute("poches", poches);

				request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
			}
			
		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
	}

}
