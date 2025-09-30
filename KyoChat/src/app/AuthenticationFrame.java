package app;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AuthenticationFrame {

	private static JFrame authFrame = new JFrame("KyoChat Login");
	
	private static AuthenticationFrame instance = new AuthenticationFrame();

	private AuthenticationFrame() {
		initialize();
	}
	
	public static AuthenticationFrame getInstance() {
		return instance;
	}
	
	private void initialize() {
		System.out.println("[AuthenticationFrame] initialize() Creating UI.");
		authFrame.setMinimumSize(new Dimension(800,600));
		authFrame.setSize(800,600);
		
		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		
		JLabel lName = new JLabel("What is your name?");
		
		
		JTextField name = new JTextField();
		name.setMinimumSize(new Dimension(300,50));
		name.setPreferredSize(new Dimension(300,50));
		
		JButton login = new JButton("Login");
		login.setMinimumSize(new Dimension(300,50));
		login.setPreferredSize(new Dimension(300,50));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		loginPanel.add(lName,gbc);
		
		gbc.gridy = 1;
		loginPanel.add(name,gbc);
		
		gbc.gridy = 2;
		loginPanel.add(login, gbc);
		
		authFrame.add(loginPanel);
		
		EnterKeyPressListener(name);
		LoginButtonPressListener(login,name);
	}
	
	public void setVisible(boolean value) {
		System.out.println("[AuthenticationFrame] setVisible() setting frame visibility to " + value);
		authFrame.setVisible(value);
	}
	
	public void setEnabled(boolean value) {
		System.out.println("[AuthenticationFrame] setVisible() setting frame enabled to " + value);
		authFrame.setEnabled(value);
	}
	
	private static void EnterKeyPressListener(JTextField name) {
		System.out.println("[KyoChat] EnterKeyPressListener() initiated...");
		name.addActionListener(e -> {
	        String value = name.getText().trim();
	        name.setText("");
	        if (!value.isEmpty()) {
	        	ServerHandler server = ServerHandler.getInstance();
	        	Authentication auth = new Authentication();
	        	
	        	boolean retcode = server.ConnectServer();
	        	if(true == retcode) {
	        		auth.login(value);
	    	        User.getInstance().setUser(value);
	        	}
	        }
	    });
	}
	
	private static void LoginButtonPressListener(JButton login, JTextField name) {
		System.out.println("[KyoChat] SendButtonPressListener() initiated...");
		login.addActionListener(e -> {
	        String value = name.getText().trim();
	        name.setText("");
	        if (!value.isEmpty()) {
	        	Authentication auth = new Authentication();
	        	boolean retcode = ServerHandler.getInstance().isServerOnline();
	        	
	        	if(true == retcode) {
	        		auth.login(value);
	    	        User.getInstance().setUser(value);
	        	}
	        }
	    });
	}

}
