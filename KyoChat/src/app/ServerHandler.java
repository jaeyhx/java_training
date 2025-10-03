package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ServerHandler {
	private Socket server;
	private static ServerHandler instance = new ServerHandler();
	private ObjectOutputStream server_message;
	private ObjectInputStream server_response;
	
	private ServerHandler() {
		ConnectServer();
	}
	
	public static ServerHandler getInstance() {
		return instance;
	}
	
	public boolean ConnectServer() {
		boolean retcode = false;
		try {
			server = new Socket(ServerHostInfo.getInstance().getHost(), ServerHostInfo.getInstance().getPort());
			server_message = new ObjectOutputStream(server.getOutputStream());
			server_response = new ObjectInputStream(server.getInputStream());
			retcode = isServerOnline();
			
			if(true == retcode) {
				// connection established.
				ServerListener listener = new ServerListener(server);
				listener.start();
				
			} else {
				// connection is closed.
			}

		} catch (Exception e) {
			// server is offline.
			e.printStackTrace();
		}
		return retcode;
	}
	
	public boolean DisconnectServer() {
		boolean retcode = false;
		try {
			server.close();
			retcode = isServerOnline();
			if(true == retcode) {
				// connection is still active.
			} else {
				// connection is closed.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retcode;
	}
	
	public boolean login(String name) {
		boolean retcode;
		System.out.println("[ServerHandler] login() logging in the user into the server.");
		try {
			HashMap<String, String> req = ServerRequestBuilder.getInstance().build("login", name);
			System.out.println("[ServerHandler] login() sending request to server.="+req.toString());
			server_message.writeObject(req);
			server_message.flush();
			//server_message = null;
			retcode = true;
		} catch (Exception e) {
			System.out.println("[ServerHandler] login() ERROR! " + e.getMessage());
			e.printStackTrace();
			retcode = false;
		}
		System.out.println("[ServerHandler] login() request sent.");
		return retcode;
	}
	
	public boolean logout(String name) {
		boolean retcode;
		System.out.println("[ServerHandler] logout() logging out the user from the server.");
		try {
			HashMap<String, String> req = ServerRequestBuilder.getInstance().build("logout", name);
			System.out.println("[ServerHandler] logout() sending request to server.="+req.toString());
			server_message.writeObject(req);
			server_message.flush();
			//server_message = null;
			retcode = true;
		} catch (Exception e) {
			System.out.println("[ServerHandler] logout() ERROR! " + e.getMessage());
			e.printStackTrace();
			retcode = false;
		}
		System.out.println("[ServerHandler] logout() request sent.");
		return retcode;
	}
	
	public boolean SendMessage(String message) {
		boolean retcode;
		try {
			HashMap<String, String> req = ServerRequestBuilder.getInstance().build("msg", message);
			System.out.println("[ServerHandler] SendMessage() sending request to server. req="+req.toString());
			server_message.writeObject(req);
			server_message.flush();
			//server_message = null;
			retcode = true;
		} catch (Exception e) {
			System.out.println("[ServerHandler] SendMessage() ERROR! " + e.getMessage());
			e.printStackTrace();
			retcode = false;
		}
		System.out.println("[ServerHandler] SendMessage() request sent.");
		return retcode;
	}
	
	public void ReceiveMessage(String command) {
		Authentication auth = new Authentication();

		switch(command) {
		case "login":
			System.out.println("[ServerHandler] ReceiveMessage() login reply received.");
			RecordPeers(ServerResponseBuilder.getInstance().getPeers());
			auth.setUserLoginStatus(true);
			break;
		case "logout":
			System.out.println("[ServerHandler] ReceiveMessage() logout reply received.");
			auth.setUserLoginStatus(false);
			deleteRecordedPeers();
			break;
		case "msg":
			System.out.println("[ServerHandler] ReceiveMessage() message received.");
			ChatClientFrame.getInstance().UpdateChat(ServerResponseBuilder.getInstance().getResponse()+"\n");
			break;
		}
	}
	
	public boolean isServerOnline() {
		boolean retcode = false;
		if(server.isConnected() && !server.isClosed()) {
			retcode = true;
		}
		return retcode;
	}
	
	public Socket getServerConnection() {
		return server;
	}
	
	private void deleteRecordedPeers() {
		Peers.getInstance().wipePeers();
	}
	
	private void RecordPeers(String value) {
		System.out.println("[ServerHandler] recordPeers() recording online peers.");
		String[] peers = value.split(",");
		
		for(int ctr=0; ctr<peers.length;ctr++) {
			System.out.println("[ServerHandler] recordPeers() name=" + peers[ctr]);
			Peers.getInstance().addPeer(peers[ctr]);
		}
	}
	
	public ObjectInputStream getObjectInputStream() {
		return server_response;
	}
}
