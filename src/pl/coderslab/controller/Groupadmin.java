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

import pl.coderslab.dao.GroupDao;
import pl.coderslab.model.Group;

@WebServlet("/groupAdmin")
public class Groupadmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Groupadmin() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		Cookie[] check = request.getCookies();
		Group[] groups = null;
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
			groups = GroupDao.loadAllGroups();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("groups", groups);

		getServletContext().getRequestDispatcher("/WEB-INF/views/groupadmin.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
