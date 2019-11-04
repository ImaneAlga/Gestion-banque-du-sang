package hpn.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.Donneur;
import hpn.system.dao.DAODonneur;
import hpn.system.form.FormDonneur;

/**
 * 
 * @author alga
 *
 */

@WebServlet("/donneur")
public class ServletDonneur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String VUE_SUCCES = "/WEB-INF/all_donneur.jsp";
	private static String VUE_FORM_AJOUT = "/WEB-INF/add_donneur.jsp";
	private static String VUE_FORM_UPDATE = "/WEB-INF/update_donneur.jsp";

	public ServletDonneur() {
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

				if (request.getParameter("action").equals("delete")) {
					Donneur donneur = new Donneur();
					DAODonneur daoDonneur = new DAODonneur();
					
					donneur = daoDonneur.selectOne(Integer.parseInt(request.getParameter("id")));
					daoDonneur.delete(donneur);

					response.sendRedirect(request.getContextPath() + "/list?var=donneur");
				}

				if (request.getParameter("action").equals("add")) {
					FormDonneur formDonneur = new FormDonneur(request);
					Donneur donneur = formDonneur.creerDonneur();
					
					if (formDonneur.getErreurs().isEmpty()) {
						DAODonneur daoDonneur = new DAODonneur();
						daoDonneur.insert(donneur);

						response.sendRedirect(request.getContextPath() + "/list?var=donneur");
					} else {
						request.setAttribute("donneur", donneur);
						request.setAttribute("erreurs", formDonneur.getErreurs());
						request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
					}
				}
				if (request.getParameter("action").equals("update")) {

					FormDonneur formDonneur = new FormDonneur(request);
					Donneur donneur = new Donneur();
					DAODonneur daoDonneur = new DAODonneur();

					System.out.println(formDonneur.getErreurs().toString());
					if (request.getParameter("id") != null) {

						donneur = formDonneur.modifierDonneur();

						System.out.println(formDonneur.getErreurs().toString());
						if (formDonneur.getErreurs().isEmpty()) {

							daoDonneur.update(donneur);
							response.sendRedirect(request.getContextPath() + "/list?var=donneur");
						} else {
							request.setAttribute("donneur", donneur);
							request.setAttribute("erreurs", formDonneur.getErreurs());
							request.getServletContext().getRequestDispatcher(VUE_FORM_UPDATE).forward(request, response);
						}
					}
					
					if (request.getParameter("var") != null) {
						
						donneur = daoDonneur.selectOne(Integer.parseInt(request.getParameter("var")));
						request.setAttribute("donneur", donneur);
						request.getServletContext().getRequestDispatcher(VUE_FORM_UPDATE).forward(request, response);
					}

				}

			} else {
				request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
			}
			
		}else {
			response.sendRedirect(request.getContextPath() + "/log");
		}

	}
}