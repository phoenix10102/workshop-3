package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.Solution;
import pl.coderslab.utils.DbUtil;

public class SolutionDao {

	public static void saveToDB(Solution solution) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		if (solution.getId() == 0) {
			String sql = "INSERT INTO Solution(created, description, exercise_id, users_id) VALUES (now(), ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, solution.getDescription());
			preparedStatement.setInt(2, solution.getExercise_id());
			preparedStatement.setInt(3, solution.getUsers_id());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				solution.setId(rs.getInt(1));
			}
			rs.close();
			rs = null;
		} else {
			String sql = "UPDATE Solution SET updated=now(), description=?, exercise_id=?, users_id=? where id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, solution.getDescription());
			preparedStatement.setInt(2, solution.getExercise_id());
			preparedStatement.setInt(3, solution.getUsers_id());
			preparedStatement.setInt(4, solution.getId());
			preparedStatement.executeUpdate();
		}
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
	}

	static public Solution loadSolutionById(int id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Solution where id=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getString("created"));
			loadedSolution.setUpdated(rs.getString("updated"));
			loadedSolution.setDescription(rs.getString("description"));
			loadedSolution.setExercise_id(rs.getInt("exercise_id"));
			loadedSolution.setUsers_id(rs.getInt("users_id"));
			rs.close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			conn.close();
			conn = null;
			return loadedSolution;
		}
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return null;
	}

	static public Solution[] loadAll() throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution";
		preparedStatement = conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getString("created"));
			loadedSolution.setUpdated(rs.getString("updated"));
			loadedSolution.setDescription(rs.getString("description"));
			loadedSolution.setExercise_id(rs.getInt("exercise_id"));
			loadedSolution.setUsers_id(rs.getInt("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return sArray;
	}

	static public Solution[] loadAll(int limit) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution ORDER BY created DESC LIMIT ?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, limit);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getString("created"));
			loadedSolution.setUpdated(rs.getString("updated"));
			loadedSolution.setDescription(rs.getString("description"));
			loadedSolution.setExercise_id(rs.getInt("exercise_id"));
			loadedSolution.setUsers_id(rs.getInt("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return sArray;
	}

	static public Solution[] loadAllByUserId(int users_id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution WHERE users_id=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, users_id);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getString("created"));
			loadedSolution.setUpdated(rs.getString("updated"));
			loadedSolution.setDescription(rs.getString("description"));
			loadedSolution.setExercise_id(rs.getInt("exercise_id"));
			loadedSolution.setUsers_id(rs.getInt("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return sArray;
	}

	static public Solution[] loadAllByExerciseId(int exercise_id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution where exercise_id=? ORDER BY IFNULL(updated, created) DESC";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, exercise_id);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getString("created"));
			loadedSolution.setUpdated(rs.getString("updated"));
			loadedSolution.setDescription(rs.getString("description"));
			loadedSolution.setExercise_id(rs.getInt("exercise_id"));
			loadedSolution.setUsers_id(rs.getInt("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return sArray;
	}

	public static void delete(Solution solution) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		if (solution.getId() != 0) {
			String sql = "DELETE FROM Solution WHERE id= ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, solution.getId());
			preparedStatement.executeUpdate();
			solution.setId(0);
			conn.close();
			conn = null;
			preparedStatement.close();
			preparedStatement = null;
		}

	}

}
