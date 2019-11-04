package hpn.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hpn.system.beans.ExamenFait;
import hpn.system.beans.Prelevement;
import hpn.system.beans.TypeExamen;
import hpn.system.dao.DAOExamenFait;
import hpn.system.dao.DAOPrelevement;
import hpn.system.dao.DAOTypeExamen;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/examfait")
public class ServletExamFait extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_LIST = "/list?var=examen_fait";

	public ServletExamFait() {
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
					int noFail = 1;

					DAOPrelevement daoPrelevement = new DAOPrelevement();
					DAOTypeExamen daoTypeExamen = new DAOTypeExamen();
					DAOExamenFait daoExamenFait = new DAOExamenFait();

					String resultat = request.getParameter("resultat");

					/*
					 * DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); Date dateExam =
					 * new Date(); try { dateExam =
					 * dateFormat.parse(request.getParameter("date_examen")); } catch
					 * (ParseException e) { noFail = 0; }
					 */
					Prelevement prelevement = new Prelevement();
					prelevement = daoPrelevement.selectOne(Integer.parseInt(request.getParameter("id_prelevement")));

					TypeExamen typeExamen = new TypeExamen();
					typeExamen = daoTypeExamen.selectOne(Integer.parseInt(request.getParameter("id_type")));

					ExamenFait examenFait = new ExamenFait();
					examenFait.setResultat(resultat);
					examenFait.setDateExamen(prelevement.getDatePrelevement());
					examenFait.setMyTypeExamen(typeExamen);
					examenFait.setMyPrelevement(prelevement);

					if (noFail != 0) {
						daoExamenFait.insert(examenFait);
					}
					int idp = prelevement.getId();

					request.setAttribute("id", idp);
					request.getServletContext().getRequestDispatcher(VUE_LIST).forward(request, response);
				}
			}
			
		}else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
		
	}
}
