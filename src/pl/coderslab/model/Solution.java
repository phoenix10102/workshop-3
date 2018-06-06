package pl.coderslab.model;

public class Solution {

	private int id;
	private String created;
	private String updated;
	private String description;
	private int exercise_id;
	private int users_id;

	public Solution() {

	}

	public Solution(String description, int exercise_id, int users_id) {

		this.description = description;
		this.exercise_id = exercise_id;
		this.users_id = users_id;

	}

	public int getId() {
		return id;
	}

	public String getCreated() {
		String created = this.created.substring(0, 10) + ", " + this.created.substring(10, this.created.length() - 2);
		return created;
	}

	public String getUpdated() {
		if (this.updated == null) {
			return "";
		}
		String updated = this.updated.substring(0, this.updated.length() - 2);
		return updated;
	}

	public String getDescription() {
		return description;
	}

	public int getExercise_id() {
		return exercise_id;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

}
