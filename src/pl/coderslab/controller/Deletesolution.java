package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/deleteSolution")
public class Deletesolution extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Deletesolution() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Cookie[] check = request.getCookies();

		HttpSession sess = request.getSession();

		if (sess.getAttribute("sessionUserAdmin") != null) {
			if (request.getParameter("soluId") != null) {
				int solId = Integer.parseInt(request.getParameter("soluId"));
				if (check != null) {
					for (Cookie c : check) {
						if ("userLogin".equals(c.getName())) {
							String userEmail = (String) sess.getAttribute("sessionUserAdmin");
							try {
								User loggedUser = UserDao.loadUserByEmail(userEmail);
								request.setAttribute("loggedu", loggedUser);
								Solution delSol = SolutionDao.loadSolutionById(solId);
								SolutionDao.delete(delSol);

							} catch (SQLException e) {
								e.printStackTrace();
							}
							request.setAttribute("useradmin", "true");
						}
					}
				}
			}
		}
		response.sendRedirect("userPanel");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
