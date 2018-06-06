package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.SolutionPage;
import pl.coderslab.utils.DbUtil;

public class SolutionPageDao {

	static public SolutionPage[] loadAll(int limit) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<SolutionPage> solutions = new ArrayList<SolutionPage>();
		String sql = "SELECT Solution.id, created, updated, Exercise.title AS exercise, Solution.description AS solution, username FROM Solution JOIN Exercise ON Solution.exercise_id=Exercise.id JOIN Users ON Solution.users_id=Users.id ORDER BY IFNULL(updated, created) DESC LIMIT ?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, limit);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			SolutionPage loadedSolution = new SolutionPage();
			loadedSolution.setSolutionId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getString("created"));
			loadedSolution.setUpdated(rs.getString("updated"));
			loadedSolution.setExercise(rs.getString("exercise"));
			loadedSolution.setSolution(rs.getString("solution"));
			loadedSolution.setUsername(rs.getString("username"));
			solutions.add(loadedSolution);
		}
		SolutionPage[] sArray = new SolutionPage[solutions.size()];
		sArray = solutions.toArray(sArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return sArray;
	}

}
