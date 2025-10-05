package server;

public class ServerHostInfo {

	private static ServerHostInfo instance = new ServerHostInfo();
	private static String host;
	private static int port;
	
	private ServerHostInfo() {
		host = "localhost";
		port = 9090;
	}
	
	public static ServerHostInfo getInstance() {
		return instance;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}

}
