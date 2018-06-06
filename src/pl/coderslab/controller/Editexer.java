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

@WebServlet("/editExer")
public class Editexer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Editexer() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		if (request.getParameter("exerId") == null) {
			response.sendRedirect("exerAdmin");
		} else {
			Cookie[] check = request.getCookies();
			Exercise[] exercises = null;
			Exercise editExer = null;
			if (sess.getAttribute("sessionAdmin") != null) {
				if (sess.getAttribute("sessionAdmin").equals("true")) {
					if (check != null) {
						for (Cookie c : check) {
							if ("panelLogin".equals(c.getName())) {
								request.setAttribute("admin", "true");
								int exerId = Integer.parseInt(request.getParameter("exerId"));
								request.setAttribute("editexer", exerId);
								try {
									exercises = ExerciseDao.loadAllExercises();
									editExer = ExerciseDao.loadExerciseById(exerId);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								request.setAttribute("exercises", exercises);
								request.setAttribute("cwicz", editExer);

							}
						}
					}
				}
			}
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
		Exercise editExer = null;
		if (sess.getAttribute("sessionAdmin") != null) {
			if (sess.getAttribute("sessionAdmin").equals("true")) {
				if (check != null) {
					for (Cookie c : check) {
						if ("panelLogin".equals(c.getName())) {
							int exerId = Integer.parseInt(request.getParameter("exerId"));
							String title = request.getParameter("title");
							String desc = request.getParameter("desc");
							try {
								if (checkIfTitleExists(title, exerId) || "".equals(title) || title.charAt(0) == ' ') {
									try {
										editExer = ExerciseDao.loadExerciseById(exerId);
										request.setAttribute("editexer", exerId);
										request.setAttribute("cwicz", editExer);
										request.setAttribute("invalidexer", "true");

									} catch (SQLException e) {
										e.printStackTrace();
									}
								} else {
									try {
										editExer = ExerciseDao.loadExerciseById(exerId);
										editExer.setTitle(title);
										editExer.setDescription(desc);
										ExerciseDao.saveToDB(editExer);
										exercises = ExerciseDao.loadAllExercises();
									} catch (SQLException e) {
										e.printStackTrace();
									}
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

	public static boolean checkIfTitleExists(String title, int exerId) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> exerTitles = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT * FROM Exercise");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt("id") != exerId) {
					String exerTitle = rs.getString("title");
					exerTitles.add(exerTitle);
				}
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
