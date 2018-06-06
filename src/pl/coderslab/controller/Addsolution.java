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

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/addSolution")
public class Addsolution extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Addsolution() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Cookie[] check = request.getCookies();
		Solution[] userSol = null;
		User loggedUser = null;
		Exercise[] allExer = null;
		int userId = -1;
		HttpSession sess = request.getSession();

		if (request.getParameter("addsol") == null) {
			response.sendRedirect("useradmin");
		} else {
			if (sess.getAttribute("sessionUserAdmin") != null) {
				if (check != null) {
					for (Cookie c : check) {
						if ("userLogin".equals(c.getName())) {
							request.setAttribute("useradmin", "true");
							String userEmail = (String) sess.getAttribute("sessionUserAdmin");
							try {
								loggedUser = UserDao.loadUserByEmail(userEmail);
								userId = loggedUser.getId();
								userSol = SolutionDao.loadAllByUserId(userId);
								allExer = ExerciseDao.loadAllExercises();
								request.setAttribute("allexer", allExer);
								request.setAttribute("userId", userId);
								request.setAttribute("usersols", userSol);
								request.setAttribute("loggedu", loggedUser);

							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			request.setAttribute("addsol", "true");
			getServletContext().getRequestDispatcher("/WEB-INF/views/userpanel.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		Cookie[] check = request.getCookies();
		Solution[] userSol = null;
		User loggedUser = null;

		if (sess.getAttribute("sessionUserAdmin") != null) {
			if (check != null) {
				for (Cookie c : check) {
					if ("userLogin".equals(c.getName())) {
						String desc = request.getParameter("desc");
						int exerId = Integer.parseInt(request.getParameter("exerid"));
						int userId = Integer.parseInt(request.getParameter("userid"));
						Solution newSol = new Solution(desc, exerId, userId);
						String userEmail = (String) sess.getAttribute("sessionUserAdmin");
						try {
							loggedUser = UserDao.loadUserByEmail(userEmail);
							SolutionDao.saveToDB(newSol);
							userSol = SolutionDao.loadAllByUserId(userId);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						request.setAttribute("loggedu", loggedUser);
						request.setAttribute("useradmin", "true");
						request.setAttribute("usersols", userSol);

					}
				}
			}
		}
		getServletContext().getRequestDispatcher("/WEB-INF/views/userpanel.jsp").forward(request, response);
	}

}
