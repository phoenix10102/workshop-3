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

import pl.coderslab.dao.GroupDao;
import pl.coderslab.model.Group;
import pl.coderslab.utils.DbUtil;

@WebServlet("/addGroup")
public class Addgroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Addgroup() {
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

		if (request.getParameter("addgroup") == null) {
			response.sendRedirect("groupAdmin");
		} else {
			if (sess.getAttribute("sessionAdmin") != null) {
				if (sess.getAttribute("sessionAdmin").equals("true")) {
					if (check != null) {
						for (Cookie c : check) {
							if ("panelLogin".equals(c.getName())) {
								request.setAttribute("admin", "true");
								try {
									groups = GroupDao.loadAllGroups();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			request.setAttribute("addnewgroup", "true");
			request.setAttribute("groups", groups);
			getServletContext().getRequestDispatcher("/WEB-INF/views/groupadmin.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
							try {
								String name = request.getParameter("name");
								if (checkIfGroupnameExists(name) || "".equals(name) || name.charAt(0) == ' ') {
									request.setAttribute("invalidgroup", "true");
									request.setAttribute("addnewgroup", "true");
									groups = GroupDao.loadAllGroups();
								} else {
									Group newGroup = new Group(name);
									GroupDao.saveToDB(newGroup);
									groups = GroupDao.loadAllGroups();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							request.setAttribute("admin", "true");
							request.setAttribute("groups", groups);
						}
					}
				}
			}
		}
		getServletContext().getRequestDispatcher("/WEB-INF/views/groupadmin.jsp").forward(request, response);
	}

	public static boolean checkIfGroupnameExists(String name) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> groupNames = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT * FROM Usergroup");
			rs = ps.executeQuery();
			while (rs.next()) {
				String grpName = rs.getString("name");
				groupNames.add(grpName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (groupNames.contains(name)) {
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
