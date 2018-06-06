package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.model.Group;

@WebServlet("/viewGroup")
public class Zadanie4 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Zadanie4() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		Group[] groups = null;

		try {
			groups = GroupDao.loadAllGroups();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("groups", groups);
		getServletContext().getRequestDispatcher("/WEB-INF/views/groups.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
