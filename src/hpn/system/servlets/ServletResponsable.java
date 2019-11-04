package hpn.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.ResponsableMedical;
import hpn.system.dao.DAOResponsableMedical;
import hpn.system.form.FormResponsable;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/responsable")
public class ServletResponsable extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String VUE_FORM_AJOUT = "/WEB-INF/add_responsable.jsp"; 
    private static String VUE_FORM_DETAIL = "/WEB-INF/detail_responsable.jsp"; 
	
    public ServletResponsable() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession().getAttribute("connected") != null) {
			
			if (request.getParameter("action") != null) {
				if (request.getParameter("action").equals("add")) {
					
					FormResponsable formResponsable = new FormResponsable(request);
					ResponsableMedical responsableMedical = new ResponsableMedical();
					
					responsableMedical = formResponsable.creerResponsable();
					if (formResponsable.getErreurs().isEmpty()) {
						
						DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
						daoResponsableMedical.insert(responsableMedical);
						
						request.getSession();
						response.sendRedirect(request.getContextPath() + "/list?var=responsables");
					}else {
						
						request.setAttribute("erreurs", formResponsable.getErreurs());
						request.setAttribute("responsable", responsableMedical);
						request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
					}
					
				}
			}else {
				request.getServletContext().getRequestDispatcher(VUE_FORM_AJOUT).forward(request, response);
			}
			
		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
		
	}

}
