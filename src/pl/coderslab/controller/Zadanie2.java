package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.SolutionPageDao;
import pl.coderslab.model.SolutionPage;

@WebServlet("/")
public class Zadanie2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Zadanie2() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		int solNumb = Integer.parseInt(getServletContext().getInitParameter("number-solutions"));
		SolutionPage[] ostatnie = null;

		try {
			ostatnie = SolutionPageDao.loadAll(solNumb);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("lista", ostatnie);
		getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
