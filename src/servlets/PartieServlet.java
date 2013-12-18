package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import partie.Partie;

/**
 * Servlet implementation class PartieServlet
 */
@WebServlet("/Partie")
public class PartieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PartieServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println("requete post sur PartieServlet");

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		int nbJoueurs = Integer.valueOf(request.getParameterValues("nbJoueurs")[0]);
		String[] nomJoueurs = {request.getParameterValues("nomJ1")[0],
		                       request.getParameterValues("nomJ2")[0],
		                       request.getParameterValues("nomJ3")[0],
		                       request.getParameterValues("nomJ4")[0],
		                       request.getParameterValues("nomJ5")[0],
		                       request.getParameterValues("nomJ6")[0]};
		boolean partieRapide = (request.getParameter("partieRapide")!=null)? true : false;

		Partie partie = new Partie(nbJoueurs, nomJoueurs, partieRapide);
		partie.jeu();
		response.sendRedirect("");
	}
}
