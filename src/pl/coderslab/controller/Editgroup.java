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

@WebServlet("/editGroup")
public class Editgroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Editgroup() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession sess = request.getSession();

		if (request.getParameter("groupId") == null) {
			response.sendRedirect("groupAdmin");
		} else {
			Cookie[] check = request.getCookies();
			Group[] groups = null;
			Group editGroup = null;
			if (sess.getAttribute("sessionAdmin") != null) {
				if (sess.getAttribute("sessionAdmin").equals("true")) {
					if (check != null) {
						for (Cookie c : check) {
							if ("panelLogin".equals(c.getName())) {
								request.setAttribute("admin", "true");
								int groupId = Integer.parseInt(request.getParameter("groupId"));
								request.setAttribute("editgroup", groupId);
								try {
									groups = GroupDao.loadAllGroups();
									editGroup = GroupDao.loadGroupById(groupId);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								request.setAttribute("groups", groups);
								request.setAttribute("grupka", editGroup);
							}
						}
					}
				}
			}
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
		Group loaded = null;
		if (sess.getAttribute("sessionAdmin") != null) {
			if (sess.getAttribute("sessionAdmin").equals("true")) {
				if (check != null) {
					for (Cookie c : check) {
						if ("panelLogin".equals(c.getName())) {
							int groupId = Integer.parseInt(request.getParameter("groupId"));
							String name = request.getParameter("name");
							try {
								if (checkIfNameExists(name, groupId) || "".equals(name) || name.charAt(0) == ' ') {
									try {
										loaded = GroupDao.loadGroupById(groupId);
										request.setAttribute("grupka", loaded);
										request.setAttribute("invalidgroup", "true");
										request.setAttribute("editgroup", groupId);
									} catch (SQLException e) {
										e.printStackTrace();
									}
								} else {
									try {
										loaded = GroupDao.loadGroupById(groupId);
										loaded.setName(name);
										GroupDao.saveToDB(loaded);
										groups = GroupDao.loadAllGroups();

									} catch (SQLException e) {
										e.printStackTrace();
									}
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

	public static boolean checkIfNameExists(String name, int group_id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> groupNames = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT * FROM Usergroup");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt("id") != group_id) {
					String groupName = rs.getString("name");
					groupNames.add(groupName);
				}
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
