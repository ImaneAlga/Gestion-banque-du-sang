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
import hpn.system.beans.Prelevement;
import hpn.system.dao.DAOPoche;
import hpn.system.dao.DAOPrelevement;
import hpn.system.form.FormPoche;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/poche")
public class ServletPoche extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VUE_FORM_ADD = "/WEB-INF/add_poche.jsp";
	private static final String VUE_FORM_LIST = "/list?var=poche";

	public ServletPoche() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * Verification de la session Utilisateur aucune session n'est ouverte, il y a
		 * redirection sur la page de connexion
		 */
		if (request.getSession().getAttribute("connected") != null) {

			if (request.getParameter("action") != null) {
				if (request.getParameter("action").equals("add")) {

					FormPoche formPoche = new FormPoche(request);
					Poche poche = new Poche();

					poche = formPoche.creerPoche();

					/**
					 * Verification de la validité des données issues du formulaire d'enregistrement
					 * d'une poche, et enregistrement de ces informations
					 */
					if (formPoche.getErreurs().isEmpty()) {

						DAOPrelevement daoPrelevement = new DAOPrelevement();
						poche.getMyPrelevement()
								.setQteRestant(poche.getMyPrelevement().getQteRestant() - poche.getQuantite());
						daoPrelevement.update(poche.getMyPrelevement());

						DAOPoche daoPoche = new DAOPoche();
						daoPoche.insert(poche);

						response.sendRedirect(request.getContextPath() + VUE_FORM_LIST);
					} else {

						DAOPrelevement daoPrelevement = new DAOPrelevement();
						List<Prelevement> prelevements = new ArrayList<Prelevement>();
						prelevements = daoPrelevement.selectAll();

						request.setAttribute("prelevements", prelevements);

						request.setAttribute("poche", poche);
						request.setAttribute("erreurs", formPoche.getErreurs());
						request.getServletContext().getRequestDispatcher(VUE_FORM_ADD).forward(request, response);
					}
				}
				if (request.getParameter("action").equals("delete")) {
					DAOPoche daoPoche = new DAOPoche();
					Poche poche = new Poche();

					poche = daoPoche.selectOne(Integer.parseInt(request.getParameter("id")));
					daoPoche.delete(poche);
					response.sendRedirect(request.getContextPath() + VUE_FORM_LIST);
				}
			} else {
				DAOPrelevement daoPrelevement = new DAOPrelevement();
				List<Prelevement> prelevements = new ArrayList<Prelevement>();
				prelevements = daoPrelevement.selectAll();

				request.setAttribute("prelevements", prelevements);

				request.getServletContext().getRequestDispatcher(VUE_FORM_ADD).forward(request, response);
			}

		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}

	}

}
