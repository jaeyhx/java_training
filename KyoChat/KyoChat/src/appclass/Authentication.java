package appclass;

import appframe.AuthenticationFrame;
import appframe.ChatClientFrame;
import server.ServerHandler;

public class Authentication {
	private static boolean userLoginStatus = false;
	private int timeout = 0;
	
	public boolean login(String username) {
		System.out.println("[Authentication] login() requesting login to server.");
		boolean retcode = false;
		retcode = ServerHandler.getInstance().serverRequest(Constants.CMD_LOGIN,username);
		System.out.println("[Authentication] login() login status=" + userLoginStatus);
		
		if(true == retcode) {
			System.out.println("[Authentication] login() sent to server!");
			while (!isUserLoggedIn() && timeout<30) {
				System.out.println("[Authentication] login() waiting for server reply...");
				System.out.println("[Authentication] login() login status=" + userLoginStatus);
				try {
					timeout++;
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("[Authentication] login() Thread sleep ERROR! " + e.getMessage());
				}
			}
			if(isUserLoggedIn()) {
		        User.getInstance().setUser(username);
				timeout=0;
				AuthenticationFrame.getInstance().setVisible(false);
				ChatClientFrame.getInstance().setVisible(true);
			} else {
				System.out.println("[Authentication] login() time out occured! please try again.");
			}
		}
		return retcode;
	}
	
	public boolean logout(String username) {
		System.out.println("[Authentication] logout() requesting logout to server.");
		boolean retcode = false;
		retcode = ServerHandler.getInstance().serverRequest(Constants.CMD_LOGOUT, username);
		System.out.println("[Authentication] logout() login status=" + userLoginStatus);
		
		if(true == retcode) {
			System.out.println("[Authentication] logout() retcode = " + retcode);
			timeout = 0;
			AuthenticationFrame.getInstance().setVisible(true);
			ChatClientFrame.getInstance().setVisible(false);
		}
		return retcode;
	}
	
	public boolean isUserLoggedIn() {
		System.out.println("[Authentication] isUserLoggedIn() status=" + userLoginStatus);
		return userLoginStatus;
	}
	
	public void setUserLoginStatus(boolean status) {
		System.out.println("[Authentication] setUserLoginStatus() user is logged in to server. status=" + userLoginStatus);
		userLoginStatus = status;
		System.out.println("[Authentication] setUserLoginStatus() user is logged in to server. status after set=" + userLoginStatus);
	}
}
