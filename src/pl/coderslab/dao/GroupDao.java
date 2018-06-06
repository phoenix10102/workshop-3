package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.Group;
import pl.coderslab.utils.DbUtil;

public class GroupDao {

	public static void saveToDB(Group group) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		if (group.getId() == 0) {
			String sql = "INSERT INTO Usergroup(name) VALUES (?)";
			String generatedColumns[] = { "ID" };
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, group.getName());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				group.setId(rs.getInt(1));
			}
			rs.close();
			rs = null;
		} else {
			String sql = "UPDATE Usergroup SET name=? where id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, group.getName());
			preparedStatement.setInt(2, group.getId());
			preparedStatement.executeUpdate();
		}
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
	}

	static public Group loadGroupById(int id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Usergroup where id=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			Group loadedGroup = new Group();
			loadedGroup.setId(rs.getInt("id"));
			loadedGroup.setName(rs.getString("name"));
			rs.close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			conn.close();
			conn = null;
			return loadedGroup;
		}
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return null;
	}

	static public Group loadGroupByName(String name) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Usergroup where name=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, name);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			Group loadedGroup = new Group();
			loadedGroup.setId(rs.getInt("id"));
			loadedGroup.setName(rs.getString("name"));
			rs.close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			conn.close();
			conn = null;
			return loadedGroup;
		}
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return null;
	}

	static public Group[] loadAllGroups() throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<Group> groups = new ArrayList<Group>();
		String sql = "SELECT * FROM Usergroup";
		preparedStatement = conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Group loadedGroup = new Group();
			loadedGroup.setId(rs.getInt("id"));
			loadedGroup.setName(rs.getString("name"));
			groups.add(loadedGroup);
		}
		Group[] gArray = new Group[groups.size()];
		gArray = groups.toArray(gArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return gArray;
	}

	public static void delete(Group group) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		if (group.getId() != 0) {
			String sql = "DELETE FROM Usergroup WHERE id= ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, group.getId());
			preparedStatement.executeUpdate();
			group.setId(0);
			conn.close();
			conn = null;
			preparedStatement.close();
			preparedStatement = null;
		}

	}

}
