package app;

public class ServerHostInfo {

	private static ServerHostInfo instance = new ServerHostInfo();
	private static String host;
	
	private ServerHostInfo() {
		host = "localhost";
	}
	
	public ServerHostInfo getInstance() {
		return instance;
	}

}
