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

import org.mindrot.jbcrypt.BCrypt;

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/userPanel")
public class Userpanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Userpanel() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Cookie[] check = request.getCookies();

		HttpSession sess = request.getSession();
		String email = "";

		if (sess.getAttribute("sessionUserAdmin") != null) {
			if (check != null) {
				for (Cookie c : check) {
					if ("userLogin".equals(c.getName())) {
						email = (String) sess.getAttribute("sessionUserAdmin");
						try {
							User loggedUser = UserDao.loadUserByEmail(email);
							int userId = loggedUser.getId();
							Solution[] userSol = SolutionDao.loadAllByUserId(userId);
							request.setAttribute("usersols", userSol);
							request.setAttribute("loggedu", loggedUser);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						request.setAttribute("useradmin", "true");
					}
				}
			}
		}
		getServletContext().getRequestDispatcher("/WEB-INF/views/userpanel.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		String email = request.getParameter("email");
		String pass = request.getParameter("pass");

		try {
			User logUser = UserDao.loadUserByEmail(email);
			if (logUser != null) {
				if (BCrypt.checkpw(pass, logUser.getPassword())) {
					int userId = logUser.getId();
					Solution[] userSol = SolutionDao.loadAllByUserId(userId);
					request.setAttribute("usersols", userSol);
					Cookie adm = new Cookie("userLogin", "true");
					adm.setMaxAge(60 * 60 * 365);
					response.addCookie(adm);
					request.setAttribute("useradmin", "true");
					request.setAttribute("loggedu", logUser);
					sess.setAttribute("sessionUserAdmin", email);
				} else {
					request.setAttribute("loginfail", "true");
				}
			} else {
				request.setAttribute("userfail", "true");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
