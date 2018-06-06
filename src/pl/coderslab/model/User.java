package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private int id;
	private String username;
	private String password;
	private String email;
	private int usergroup_id;

	public User() {

	}
	
	public User(int id, String username, String password, String email, int usergroup_id) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.usergroup_id = usergroup_id;
	}

	public User(String username, String password, String email, int usergroup_id) {
		this.username = username;
		this.email = email;
		setPassword(password);
		this.usergroup_id = usergroup_id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUsergroup_id() {
		return usergroup_id;
	}

	public void setUsergroup_id(int usergroup_id) {
		this.usergroup_id = usergroup_id;
	}

}