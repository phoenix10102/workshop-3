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

import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

@WebServlet("/panel")
public class Zadanie7 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Zadanie7() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Cookie[] check = request.getCookies();

		HttpSession sess = request.getSession();
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
		User[] users = null;
		try {
			users = UserDao.loadAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("users", users);
		getServletContext().getRequestDispatcher("/WEB-INF/views/panel.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		String setLogin = getServletContext().getInitParameter("login");
		String setPass = getServletContext().getInitParameter("password");

		String login = request.getParameter("login");
		String pass = request.getParameter("pass");

		if (login.equals(setLogin) && pass.equals(setPass)) {
			Cookie adm = new Cookie("panelLogin", "true");
			adm.setMaxAge(60 * 60 * 365);
			response.addCookie(adm);
			request.setAttribute("admin", "true");
			sess.setAttribute("sessionAdmin", "true");
		} else {
			request.setAttribute("authFalse", "true");
		}
		doGet(request, response);
	}

}
