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
import pl.coderslab.model.Exercise;

@WebServlet("/exerAdmin")
public class Exerciseadmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Exerciseadmin() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		Cookie[] check = request.getCookies();
		Exercise[] exercises = null;
		if (sess.getAttribute("sessionAdmin") != null) {
			if (sess.getAttribute("sessionAdmin").equals("true")) {
				if (check != null) {
					for (Cookie c : check) {
						if ("panelLogin".equals(c.getName())) {
							request.setAttribute("admin", "true");
						}
					}
				}
			}
		}
		try {
			exercises = ExerciseDao.loadAllExercises();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("exercises", exercises);

		getServletContext().getRequestDispatcher("/WEB-INF/views/exeradmin.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
