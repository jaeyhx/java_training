package app;

public class ChatClientApp {

	public ChatClientApp() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {

		System.out.println("[KyoChat] Starting app...");
		ServerHandler.getInstance().ConnectServer();
		AuthenticationFrame.getInstance().setVisible(true);
	}
}
