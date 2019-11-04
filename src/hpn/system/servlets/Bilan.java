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

import hpn.system.beans.Poche;
import hpn.system.dao.DAOPoche;

/**
 * 
 * @author alga
 *
 */
@WebServlet("/bilan")
public class Bilan extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String BILAN_PAGE = "/WEB-INF/bilan.jsp";
	private static final String VUE_SUCCES = "/WEB-INF/index.jsp";
	private static final String VUE_ECHEC = "/login.jsp";
	private static final String ATT_LIST_POCHES = "poches";

	private static final String POURCENT_A0 = "am";
	private static final String POURCENT_A1 = "ap";
	private static final String POURCENT_B0 = "bm";
	private static final String POURCENT_B1 = "bp";
	private static final String POURCENT_AB0 = "abm";
	private static final String POURCENT_O0 = "om";
	private static final String POURCENT_O1 = "op";
	private static final String POURCENT_AB1 = "abp";
	private static final String TOTAL_POCHE = "total";

	public Bilan() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("connected") != null) {
			genererBilan(request);
			request.getServletContext().getRequestDispatcher(BILAN_PAGE).forward(request, response);

		} else {
			response.sendRedirect(request.getContextPath() + "/log");
		}
	}

	private void genererBilan(HttpServletRequest request) {

		DAOPoche daoPoche = new DAOPoche();
		List<Poche> allPoches = new ArrayList<Poche>();

		allPoches = daoPoche.selectAll();

		Integer nTotal = allPoches.size();

		Integer percenA0 = rapportPoche("A", "RH-", nTotal);
		Integer percenA1 = rapportPoche("A", "RH+", nTotal);
		Integer percenB0 = rapportPoche("B", "RH-", nTotal);
		Integer percenB1 = rapportPoche("B", "RH+", nTotal);
		Integer percenAB0 = rapportPoche("AB", "RH-", nTotal);
		Integer percenAB1 = rapportPoche("AB", "RH+", nTotal);
		Integer percenO0 = rapportPoche("O", "RH-", nTotal);
		Integer percenO1 = rapportPoche("O", "RH+", nTotal);

		Integer A0 = countPoche("A", "RH-");
		Integer A1 = countPoche("A", "RH+");
		Integer B0 = countPoche("B", "RH-");
		Integer B1 = countPoche("B", "RH+");
		Integer AB0 = countPoche("AB", "RH-");
		Integer AB1 = countPoche("AB", "RH+");
		Integer O0 = countPoche("O", "RH-");
		Integer O1 = countPoche("O", "RH+");

		HttpSession session = request.getSession();

		session.setAttribute(TOTAL_POCHE, nTotal);
		session.setAttribute(POURCENT_A0, percenA0);
		session.setAttribute(POURCENT_A1, percenA1);
		session.setAttribute(POURCENT_B0, percenB0);
		session.setAttribute(POURCENT_B1, percenB1);
		session.setAttribute(POURCENT_AB0, percenAB0);
		session.setAttribute(POURCENT_O0, percenO0);
		session.setAttribute(POURCENT_O1, percenO1);
		session.setAttribute(POURCENT_AB1, percenAB1);

		session.setAttribute("na0", A0);
		session.setAttribute("na1", A1);
		session.setAttribute("nb0", B0);
		session.setAttribute("nb1", B1);
		session.setAttribute("nab0", AB0);
		session.setAttribute("no0", O0);
		session.setAttribute("no1", O1);
		session.setAttribute("nab1", AB1);

	}

	private Integer rapportPoche(String gs, String rs, Integer nTotal) {

		DAOPoche daoPoche = new DAOPoche();

		Integer nGroupe = daoPoche.selectAdv(gs, rs).size();
		Integer percen = (int) ((Integer) 100 * ((double) nGroupe / nTotal));

		return percen;
	}

	private Integer countPoche(String gs, String rs) {

		DAOPoche daoPoche = new DAOPoche();

		Integer nGroupe = daoPoche.selectAdv(gs, rs).size();
		return nGroupe;
	}

}
