package pl.coderslab.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.utils.DbUtil;

@WebServlet("/addExer")
public class Addexercise extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Addexercise() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Cookie[] check = request.getCookies();
		Exercise[] exercises = null;
		HttpSession sess = request.getSession();

		if (request.getParameter("addexer") == null) {
			response.sendRedirect("exerAdmin");
		} else {
			if (sess.getAttribute("sessionAdmin") != null) {
				if (sess.getAttribute("sessionAdmin").equals("true")) {
					if (check != null) {
						for (Cookie c : check) {
							if ("panelLogin".equals(c.getName())) {
								request.setAttribute("admin", "true");
								try {
									exercises = ExerciseDao.loadAllExercises();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}

					}
				}
			}
			request.setAttribute("addnewexer", "true");
			request.setAttribute("exercises", exercises);
			getServletContext().getRequestDispatcher("/WEB-INF/views/exeradmin.jsp").forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
							try {
								String title = request.getParameter("title");
								String desc = request.getParameter("desc");
								if (checkIfTitleExists(title) || "".equals(title) || title.charAt(0) == ' ') {
									request.setAttribute("invalidexer", "true");
									request.setAttribute("addnewexer", "true");
									exercises = ExerciseDao.loadAllExercises();

								} else {
									Exercise newExer = new Exercise(title, desc);
									ExerciseDao.saveToDB(newExer);
									exercises = ExerciseDao.loadAllExercises();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							request.setAttribute("admin", "true");
							request.setAttribute("exercises", exercises);
						}
					}
				}
			}
		}
		getServletContext().getRequestDispatcher("/WEB-INF/views/exeradmin.jsp").forward(request, response);
	}

	public static boolean checkIfTitleExists(String title) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> exerTitles = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT * FROM Exercise");
			rs = ps.executeQuery();
			while (rs.next()) {
				String exerTitle = rs.getString("title");
				exerTitles.add(exerTitle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (exerTitles.contains(title)) {
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			return true;
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		conn.close();
		conn = null;
		return false;
	}

}
