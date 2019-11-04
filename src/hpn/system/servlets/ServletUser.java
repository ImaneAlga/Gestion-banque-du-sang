package hpn.system.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.Compte;
import hpn.system.beans.ResponsableMedical;
import hpn.system.dao.DAOResponsableMedical;
import hpn.system.form.FormResponsable;

/**
 * 
 * @author alga 
 * 
 */
@WebServlet("/user")
public class ServletUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ServletUser() {
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
				if (request.getParameter("action").equals("new")) {
					ResponsableMedical responsableMedical = new ResponsableMedical();
					DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

					responsableMedical = daoResponsableMedical.selectOne(Integer.parseInt(request.getParameter("var")));
					request.setAttribute("responsable", responsableMedical);
					request.getServletContext().getRequestDispatcher("/WEB-INF/add_compte.jsp").forward(request,
							response);
				}

				if (request.getParameter("action").equals("add")) {

					FormResponsable form = new FormResponsable(request);
					Compte compte = new Compte();
					compte = form.creerCompte();

					if (form.getErreurs().isEmpty()) {
						response.sendRedirect(request.getContextPath() + "/list?var=responsable&id="
								+ compte.getMyResponsableMedical().getId());
					} else {
						System.out.println(form.getErreurs().toString());
						request.setAttribute("erreurs", form.getErreurs());
						request.setAttribute("responsable", compte.getMyResponsableMedical());
						request.setAttribute("compte", compte);
						request.getServletContext().getRequestDispatcher("/WEB-INF/add_compte.jsp").forward(request,
								response);
					}
				}
			}

		} else {
			request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
