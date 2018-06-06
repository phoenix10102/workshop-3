package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Solution;

@WebServlet("/viewSolution")
public class Zadanie3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Zadanie3() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		int id = -1;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e1) {
			request.setAttribute("noparam", "true");
		}

		Solution sol = null;

		try {
			sol = SolutionDao.loadSolutionById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("solu", sol);
		getServletContext().getRequestDispatcher("/WEB-INF/views/solution.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
