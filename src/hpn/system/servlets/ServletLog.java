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

import hpn.system.beans.Compte;
import hpn.system.beans.Poche;
import hpn.system.dao.DAOPoche;
import hpn.system.form.FormLogin;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/log")
public class ServletLog extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String ATT_TYPE = "typeUser";
	private static final String ATT_NOM = "nomUser";
	private static final String ATT_CONNECTED = "connected";
	private static final String VUE_SUCCES = "/WEB-INF/index.jsp";
	private static final String VUE_ECHEC = "/login.jsp";
	private static final String ATT_LIST_POCHES = "poches";

	public ServletLog() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FormLogin formLogin = new FormLogin(request);
		Compte compte = formLogin.validerCompte();

		if (compte.getLogin() != null) {

			DAOPoche daoPoche = new DAOPoche();
			List<Poche> poches = new ArrayList<Poche>();
			poches = daoPoche.selectAllInvalide();
			HttpSession session = request.getSession();

			session.setAttribute(ATT_LIST_POCHES, poches);
			session.setAttribute(ATT_NOM, compte.getLogin());
			session.setAttribute(ATT_TYPE, compte.getType());
			session.setAttribute(ATT_CONNECTED, "connected");

			request.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
			// response.sendRedirect(request.getContextPath() + VUE_SUCCES);
		} else {
			request.setAttribute("error", "erreur connection");
			request.getServletContext().getRequestDispatcher(VUE_ECHEC).forward(request, response);

		}
	}

}
