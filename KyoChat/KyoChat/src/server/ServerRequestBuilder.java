package server;

import java.util.HashMap;

import appclass.Constants;

public class ServerRequestBuilder {
	private HashMap<String, String> request;
	private static ServerRequestBuilder instance = new ServerRequestBuilder();
	
	public ServerRequestBuilder() {
	}
	
	public static ServerRequestBuilder getInstance() {
		return instance;
	}

	public HashMap<String,String> build( String command, String value) {
		request = new HashMap<String,String>();
		System.out.println("[RequestBuilder] build() generating server request.");
		System.out.println("[RequestBuilder] build() command=" + command + " value=" + value);
		try {
			switch(command) {
			case Constants.CMD_LOGIN:
				request.put(Constants.KEY_NAME, value);
				request.put(Constants.CMD, command);
				break;
			case Constants.CMD_LOGOUT:
				request.put(Constants.CMD, command);
				break;
			case Constants.CMD_MSG:
				request.put(Constants.KEY_MSG, value);
				request.put(Constants.CMD, command);
				break;
			default:
				System.out.println("[RequestBuilder] build() does not recognize the command.");
				break;
			}
		} catch(Exception e) {
			System.out.println("[RequestBuilder] build() ERROR: " + e.getMessage());
		}
		System.out.println("[RequestBuilder] build() request generated! " + request.toString());
		return request;
	}
}
