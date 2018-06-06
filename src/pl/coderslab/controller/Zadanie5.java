package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

@WebServlet("/viewUsers")
public class Zadanie5 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Zadanie5() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		User[] users = null;

		int groupId = -1;
		try {
			groupId = Integer.parseInt(request.getParameter("groupId"));
		} catch (NumberFormatException e1) {
			request.setAttribute("noparam", "true");
		}
		try {
			users = UserDao.loadAllByGroupId(groupId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("users", users);
		request.setAttribute("groupid", groupId);
		getServletContext().getRequestDispatcher("/WEB-INF/views/grpusers.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
