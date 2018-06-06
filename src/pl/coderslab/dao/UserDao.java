package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.User;
import pl.coderslab.utils.DbUtil;

public class UserDao {

	public static void saveToDB(User user) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		if (user.getId() == 0) {
			String sql = "INSERT INTO Users(username, email, password, usergroup_id) VALUES (?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setInt(4, user.getUsergroup_id());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getInt(1));
			}
			rs.close();
			rs = null;
		} else {
			String sql = "UPDATE Users SET username=?, email=?, password=?, usergroup_id=? where id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setInt(4, user.getUsergroup_id());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.executeUpdate();
		}
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
	}

	static public User loadUserById(int id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Users where id=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			int userId = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String userEmail = rs.getString("email");
			int usergroup_id = rs.getInt("usergroup_id");
			User loadedUser = new User(userId, username, password, userEmail, usergroup_id);
			rs.close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			conn.close();
			conn = null;
			return loadedUser;
		}
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return null;
	}

	static public User loadUserByEmail(String email) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Users WHERE email=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, email);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String userEmail = rs.getString("email");
			int usergroup_id = rs.getInt("usergroup_id");
			User loadedUser = new User(id, username, password, userEmail, usergroup_id);
			rs.close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			conn.close();
			conn = null;
			return loadedUser;
		}
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return null;
	}

	static public User[] loadAllUsers() throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM Users";
		preparedStatement = conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String userEmail = rs.getString("email");
			int usergroup_id = rs.getInt("usergroup_id");
			User loadedUser = new User(id, username, password, userEmail, usergroup_id);
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return uArray;
	}

	static public User[] loadAllByGroupId(int usergroup_id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM Users WHERE usergroup_id=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, usergroup_id);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String userEmail = rs.getString("email");
			int usergrp_id = rs.getInt("usergroup_id");
			User loadedUser = new User(id, username, password, userEmail, usergrp_id);
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return uArray;
	}

	public static void delete(User user) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		if (user.getId() != 0) {
			String sql = "DELETE FROM Users WHERE id= ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.executeUpdate();
			user.setId(0);
			conn.close();
			conn = null;
			preparedStatement.close();
			preparedStatement = null;
		}
	}

}
