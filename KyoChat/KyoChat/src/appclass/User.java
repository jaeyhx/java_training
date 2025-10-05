package appclass;

import java.awt.Color;

public class User {

	private static User instance = new User();
	private String user;
	private Color color;
	
	private User() {
		user = "";
		color = null;
	}
	public static User getInstance() {
		return instance;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}