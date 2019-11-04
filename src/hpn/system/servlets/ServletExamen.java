package hpn.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.GroupeExamen;
import hpn.system.beans.TypeExamen;
import hpn.system.dao.DAOGroupeExamen;
import hpn.system.dao.DAOTypeExamen;

/**
 * Servlet d'ajout modification et suppression d'un groupe d'examen ou d'un type
 * dexamen
 * 
 * @author alga
 *
 */
@WebServlet("/exam")
public class ServletExamen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String VUE_LIST_GROUPE = "/list?var=groupe";
	private static String VUE_LIST_TYPE = "/list?var=type";

	public ServletExamen() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getSession().getAttribute("connected") != null) {
			
			if (request.getParameter("action") != null && request.getParameter("action").trim().length() != 0) {

				if (request.getParameter("action").equals("add")) {

					if (request.getParameter("var").equals("groupe")) {

						String nomGroupe = request.getParameter("nom_groupe");

						DAOGroupeExamen daoGroupeExamen = new DAOGroupeExamen();

						GroupeExamen groupeExamen = new GroupeExamen();

						groupeExamen = daoGroupeExamen.selectOneName(nomGroupe);

						if (groupeExamen.getNomGroupe() == null) {

							GroupeExamen groupeExamen2 = new GroupeExamen();

							groupeExamen2.setNomGroupe(nomGroupe);

							daoGroupeExamen.insert(groupeExamen2);

							response.sendRedirect(request.getContextPath() + VUE_LIST_GROUPE);
						} else {
							request.getServletContext().getRequestDispatcher(VUE_LIST_GROUPE).forward(request, response);
						}
					}

					if (request.getParameter("var").equals("type")) {

						DAOGroupeExamen daoGroupeExamen = new DAOGroupeExamen();

						GroupeExamen groupeExamen = new GroupeExamen();

						groupeExamen = daoGroupeExamen.selectOne(Integer.parseInt(request.getParameter("groupe_type")));

						DAOTypeExamen daoTypeExamen = new DAOTypeExamen();

						TypeExamen typeExamen = new TypeExamen();

						String nomType = request.getParameter("nom_type");

						typeExamen = daoTypeExamen.selectOneName(nomType);

						if (typeExamen.getNomType() == null) {

							TypeExamen typeExamen2 = new TypeExamen();

							typeExamen2.setNomType(nomType);
							typeExamen2.setMyGroupeExamen(groupeExamen);

							daoTypeExamen.insert(typeExamen2);

							response.sendRedirect(request.getContextPath() + VUE_LIST_TYPE);

						} else {
							request.getServletContext().getRequestDispatcher(VUE_LIST_TYPE).forward(request, response);
						}
					}

				} else {
					request.getServletContext().getRequestDispatcher(VUE_LIST_GROUPE).forward(request, response);
				}

			}
			
		}else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
	}
}
