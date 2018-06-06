package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.Exercise;
import pl.coderslab.utils.DbUtil;

public class ExerciseDao {

	public static void saveToDB(Exercise exercise) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		if (exercise.getId() == 0) {
			String sql = "INSERT INTO Exercise(title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, exercise.getTitle());
			preparedStatement.setString(2, exercise.getDescription());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				exercise.setId(rs.getInt(1));
			}
			rs.close();
			rs = null;
		} else {
			String sql = "UPDATE Exercise SET title=?, description=? where id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, exercise.getTitle());
			preparedStatement.setString(2, exercise.getDescription());
			preparedStatement.setInt(3, exercise.getId());
			preparedStatement.executeUpdate();
		}
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
	}

	static public Exercise loadExerciseById(int id) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Exercise where id=?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.setId(rs.getInt("id"));
			loadedExercise.setTitle(rs.getString("title"));
			loadedExercise.setDescription(rs.getString("description"));
			rs.close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			conn.close();
			conn = null;
			return loadedExercise;
		}
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return null;
	}

	static public Exercise[] loadAllExercises() throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT * FROM Exercise";
		preparedStatement = conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.setId(rs.getInt("id"));
			loadedExercise.setTitle(rs.getString("title"));
			loadedExercise.setDescription(rs.getString("description"));
			exercises.add(loadedExercise);
		}
		Exercise[] eArray = new Exercise[exercises.size()];
		eArray = exercises.toArray(eArray);
		rs.close();
		rs = null;
		preparedStatement.close();
		preparedStatement = null;
		conn.close();
		conn = null;
		return eArray;
	}

	public static void delete(Exercise exercise) throws SQLException {
		Connection conn = DbUtil.getConn();
		PreparedStatement preparedStatement = null;
		if (exercise.getId() != 0) {
			String sql = "DELETE FROM Exercise WHERE id= ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, exercise.getId());
			preparedStatement.executeUpdate();
			exercise.setId(0);
			conn.close();
			conn = null;
			preparedStatement.close();
			preparedStatement = null;
		}

	}

}
