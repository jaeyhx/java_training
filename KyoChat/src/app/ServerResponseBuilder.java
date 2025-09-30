package app;

import java.util.HashMap;

public class ServerResponseBuilder {

	private static ServerResponseBuilder instance = new ServerResponseBuilder();
	private String cmd;
	private String name;
	private String host;
	private String timestamp;
	private String peers;
	private String msg;
	
	private ServerResponseBuilder() {
		cmd = "";
		name = "";
		host = "";
		timestamp = "";
		peers = "";
		msg = "";
	}
	
	public static ServerResponseBuilder getInstance() {
		return instance;
	}
	
	public void ResponseExtractor(HashMap<String, String> response) {
		cmd = (String) response.get("cmd");
		
		switch(cmd) {
		case "login":
			name = (String) response.get("name");
			host = (String) response.get("host");
			peers = (String) response.get("peers");
			timestamp = (String) response.get("timestamp");
			break;
		case "logout":
			name = (String) response.get("name");
			host = (String) response.get("host");
			timestamp = (String) response.get("timestamp");
			break;
		case "msg":
			name = (String) response.get("name");
			host = (String) response.get("host");
			msg = (String) response.get("msg");
			timestamp = (String) response.get("timestamp");
			break;
		}
	}
	
	public String getCommand() {
		return cmd;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getMessage() {
		return msg;
	}
	
	public String getPeers() {
		return peers;
	}
	
	public String getResponse() {
		String response = "";
		
		switch(cmd) {
		case "login":
			response = "[Login] Name: " + name + " Time: "+ timestamp;
			break;
		case "msg":
			response = name + " "+ timestamp + " " + msg;
			break;
		}
		
		return response;
	}
}
