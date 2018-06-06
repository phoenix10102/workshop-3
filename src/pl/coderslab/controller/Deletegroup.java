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

@WebServlet("/deleteGroup")
public class Deletegroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Deletegroup() {
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
				if (request.getParameter("groupId") != null) {
					int groupId = Integer.parseInt(request.getParameter("groupId"));
					if (check != null) {
						for (Cookie c : check) {
							if ("panelLogin".equals(c.getName())) {
								try {
									Group delGroup = GroupDao.loadGroupById(groupId);
									GroupDao.delete(delGroup);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}

					}
				}
			}
		}
		response.sendRedirect("groupAdmin");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
