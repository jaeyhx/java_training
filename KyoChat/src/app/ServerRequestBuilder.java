package app;

import java.util.HashMap;

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
			case "login":
				request.put("name", value);
				request.put("cmd", command);
				break;
			case "logout":
				request.put("cmd", command);
				break;
			case "msg":
				request.put("msg", value);
				request.put("cmd", command);
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
