package pl.coderslab.model;

public class SolutionPage {

	private int solutionId;
	private String created;
	private String updated;
	private String exercise;
	private String solution;
	private String username;

	public SolutionPage() {

	}

	public SolutionPage(int solutionId, String created, String updated, String exercise, String solution,
			String username) {
		this.solutionId = solutionId;
		this.created = created;
		this.updated = updated;
		this.exercise = exercise;
		this.solution = solution;
		this.username = username;
	}

	public int getSolutionId() {
		return solutionId;
	}

	public String getCreated() {
		String created = this.created.substring(0, 10);
		return created;
	}

	public String getUpdated() {
		String updatedStr = "";
		if (this.updated == null) {
			updatedStr = "never";
		} else {
			updatedStr = this.updated.substring(0, 10);
		}
		return updatedStr;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getExercise() {
		return exercise;
	}

	public String getSolution() {
		if (this.solution.length() < 30) {
			return solution;
		} else {
			String solution = this.solution.substring(0, 30) + " ...";
			return solution;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
