package app;

public class User {

	private static User instance = new User();
	private String user;
	
	private User() {
		this.user = "";
	}
	public static User getInstance() {
		return instance;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}
}