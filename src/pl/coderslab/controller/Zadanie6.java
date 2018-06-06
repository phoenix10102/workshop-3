package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/user")
public class Zadanie6 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Zadanie6() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		int userId = -1;
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (NumberFormatException e1) {
			request.setAttribute("noparam", "true");
		}
		User usr = null;
		Solution[] solUser = null;

		try {
			usr = UserDao.loadUserById(userId);
			solUser = SolutionDao.loadAllByUserId(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("usr", usr);
		request.setAttribute("soluser", solUser);
		getServletContext().getRequestDispatcher("/WEB-INF/views/viewuser.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
