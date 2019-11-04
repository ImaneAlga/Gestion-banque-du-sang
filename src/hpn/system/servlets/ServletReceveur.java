package hpn.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.Receveur;
import hpn.system.dao.DAOReceveur;
import hpn.system.form.FormReceveur;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/receveur")
public class ServletReceveur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_FORM_AJOUT = "WEB-INF/add_receveur.jsp";

	public ServletReceveur() {
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

					FormReceveur formReceveur = new FormReceveur(request);
					Receveur receveur = new Receveur();

					receveur = formReceveur.creerReceveur();

					if (formReceveur.getErreurs().isEmpty()) {
						DAOReceveur daoReceveur = new DAOReceveur();
						daoReceveur.insert(receveur);
						response.sendRedirect(request.getContextPath() + "/list?var=receveur");
					} else {
						request.setAttribute("receveur", receveur);
						request.setAttribute("erreurs", formReceveur.getErreurs());
						request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
					}
				}
			} else {
				request.getServletContext().getRequestDispatcher("/WEB-INF/add_receveur.jsp").forward(request, response);
			}
			
		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
	}

}
