package app;

public class Authentication {
	private static boolean userLoginStatus = false;
	
	public boolean login(String username) {
		System.out.println("[Authentication] login() requesting login to server.");
		boolean retcode = false;
		retcode = ServerHandler.getInstance().login(username);
		System.out.println("[Authentication] login() login status=" + userLoginStatus);
		
		if(true == retcode) {
			System.out.println("[Authentication] login() sent to server!");
			while (!isUserLoggedIn()) {
				System.out.println("[Authentication] login() waiting for server reply...");
				System.out.println("[Authentication] login() login status=" + userLoginStatus);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("[Authentication] login() Thread sleep ERROR! " + e.getMessage());
				}
			}
			AuthenticationFrame.getInstance().setVisible(false);
			ChatClientFrame.getInstance().setVisible(true);
		}
		return retcode;
	}
	
	public boolean logout(String username) {
		System.out.println("[Authentication] logout() requesting login to server.");
		boolean retcode = false;
		retcode = ServerHandler.getInstance().logout(username);
		System.out.println("[Authentication] logout() login status=" + userLoginStatus);
		
		if(true == retcode) {
			System.out.println("[Authentication] logout() sent to server!");
			while (!isUserLoggedIn()) {
				System.out.println("[Authentication] logout() waiting for server reply...");
				System.out.println("[Authentication] logout() login status=" + userLoginStatus);
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					System.out.println("[Authentication] logout() Thread sleep ERROR! " + e.getMessage());
				}
			}
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
