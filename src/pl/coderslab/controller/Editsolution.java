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
import pl.coderslab.model.Solution;

@WebServlet("/editSolution")
public class Editsolution extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Editsolution() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		if (request.getParameter("soluId") == null) {
			response.sendRedirect("userPanel");
		} else {
			Cookie[] check = request.getCookies();
			Solution[] userSol = null;
			Solution editSol = null;
			if (sess.getAttribute("sessionUserAdmin") != null) {
				if (check != null) {
					for (Cookie c : check) {
						if ("userLogin".equals(c.getName())) {
							int solId = Integer.parseInt(request.getParameter("soluId"));
							try {
								editSol = SolutionDao.loadSolutionById(solId);
								
								
								
							} catch (SQLException e) {
								e.printStackTrace();
							}
							request.setAttribute("editsol", editSol);
							request.setAttribute("useradmin", "true");
							request.setAttribute("usersols", userSol);
						}
					}
				}
			}
			getServletContext().getRequestDispatcher("/WEB-INF/views/userpanel.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Solution[] userSol = null;
		try {
			int solId = Integer.parseInt(request.getParameter("soluId"));
			Solution editSol = SolutionDao.loadSolutionById(solId);
			editSol.setDescription(request.getParameter("desc"));
			int exerId = Integer.parseInt(request.getParameter("exerid"));
			editSol.setExercise_id(exerId);
			int userId = Integer.parseInt(request.getParameter("userid"));
			editSol.setUsers_id(userId);
			SolutionDao.saveToDB(editSol);
			userSol = SolutionDao.loadAllByUserId(userId);
			request.setAttribute("useradmin", "true");
			request.setAttribute("usersols", userSol);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		getServletContext().getRequestDispatcher("/WEB-INF/views/userpanel.jsp").forward(request, response);
	}

}
