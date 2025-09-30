package app;

import java.util.HashMap;

public class ServerRequestBuilder {
	private HashMap<String, String> request;
	
	public ServerRequestBuilder() {
		request = new HashMap<String,String>();
		// TODO Auto-generated constructor stub
	}

	public HashMap<String,String> build( String command, String value) {
		System.out.println("[RequestBuilder] build() generating server request.");
		System.out.println("[RequestBuilder] build() command=" + command + " value=" + value);
		try {
			switch(command) {
			case "login":
				request.put("cmd", command);
				request.put("name", value);
				break;
			case "logout":
				request.put("cmd", command);
				break;
			case "msg":
				request.put("cmd", command);
				request.put("msg", value);
				break;
			default:
				System.out.println("[RequestBuilder] build() does not recognize the command.");
				break;
			}
		} catch(Exception e) {
			System.out.println("[RequestBuilder] build() ERROR: " + e.getMessage());
		}
		System.out.println("[RequestBuilder] build() request generated!");
		return request;
	}
}
