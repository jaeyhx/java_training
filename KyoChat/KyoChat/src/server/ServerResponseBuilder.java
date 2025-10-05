package server;

import java.util.HashMap;

import appclass.Constants;

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
		cmd = (String) response.get(Constants.CMD);
		switch(cmd) {
		case Constants.CMD_LOGIN:
			name = (String) response.get(Constants.KEY_NAME);
			host = (String) response.get(Constants.KEY_HOST);
			peers = (String) response.get(Constants.KEY_PEERS);
			timestamp = (String) response.get(Constants.KEY_TIMESTAMP);
			break;
		case Constants.CMD_LOGOUT:
			name = (String) response.get(Constants.KEY_NAME);
			host = (String) response.get(Constants.KEY_HOST);
			timestamp = (String) response.get(Constants.KEY_TIMESTAMP);
			break;
		case Constants.CMD_MSG:
			name = (String) response.get(Constants.KEY_NAME);
			host = (String) response.get(Constants.KEY_HOST);
			msg = (String) response.get(Constants.KEY_MSG);
			timestamp = (String) response.get(Constants.KEY_TIMESTAMP);
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
	
	public String getResponseMessage() {
		String response = "";
		
		switch(cmd) {
		case Constants.CMD_LOGIN:
			response = name + " entered the chat at "+ timestamp + ".";
			break;
		case Constants.CMD_LOGOUT:
			response = name + " left the chat at "+ timestamp + ".";
			break;
		case Constants.CMD_MSG:
			response = name + ": " + msg;
			break;
		}
		
		return response;
	}
}
