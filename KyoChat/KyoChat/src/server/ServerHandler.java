package server;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import appclass.Authentication;
import appclass.Peer;
import appclass.Peers;
import appclass.User;
import appframe.ChatClientFrame;

public class ServerHandler {
	private Socket server;
	private static ServerHandler instance = new ServerHandler();
	private ObjectOutputStream server_request;
	private ObjectInputStream server_response;
	private ServerListener listener;
	
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
			server_request = new ObjectOutputStream(server.getOutputStream());
			server_response = new ObjectInputStream(server.getInputStream());
			retcode = isServerOnline();
			
			if(true == retcode) {
				// connection established.
				listener = new ServerListener();
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
	
	public boolean serverRequest(String cmd, String value) {
		boolean sendStatus = false;
		try {
			HashMap<String, String> req = ServerRequestBuilder.getInstance().build(cmd, value);
			System.out.println("[ServerHandler] serverRequest() sending request to server. req="+req.toString());
			server_request.writeObject(req);
			server_request.flush();
			//server_message = null;
			sendStatus = true;
		} catch (Exception e) {
			System.out.println("[ServerHandler] serverRequest() ERROR! " + e.getMessage());
			e.printStackTrace();
			sendStatus = false;
		}
		System.out.println("[ServerHandler] serverRequest() request sent.");
		return sendStatus;
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
			if(ServerResponseBuilder.getInstance().getName().equals(User.getInstance().getUser())) {
				System.out.println("[ServerHandler] ReceiveMessage() user is logging out.");
				auth.setUserLoginStatus(false);
				Peers.getInstance().wipePeers();
			} else {
				System.out.println("[ServerHandler] ReceiveMessage() user " + ServerResponseBuilder.getInstance().getName() + " is logging out.");
			}
			break;
		case "msg":
			HashMap<String, Color> peerslist = Peers.getInstance().getPeers();
			String name = ServerResponseBuilder.getInstance().getName();
			System.out.println("[ServerHandler] ReceiveMessage() message received.");
			System.out.println("[ServerHandler] ReceiveMessage() message received. received from: " + name);
			System.out.println("[ServerHandler] ReceiveMessage() message received. peers count: " + peerslist.size());
			System.out.println("[ServerHandler] ReceiveMessage() message received. received color: " + peerslist.get(name));
			System.out.println("[ServerHandler] ReceiveMessage() message received. msg=: " + ServerResponseBuilder.getInstance().getMessage());
			
			ChatClientFrame.getInstance().UpdateChat(name, peerslist.get(name),ServerResponseBuilder.getInstance().getMessage());
			//ChatClientFrame.getInstance().UpdateChat((Peers.getInstance().getPeers()).get(ServerResponseBuilder.getInstance().getName()), ServerResponseBuilder.getInstance().getResponseMessage()+"\n");
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
	
	private void RecordPeers(String value) {
		System.out.println("[ServerHandler] recordPeers() recording online peers.");
		String[] peers = value.split(",");
		
		for(int ctr=0; ctr<peers.length;ctr++) {
			System.out.println("[ServerHandler] recordPeers() name=" + peers[ctr]);
			ChatClientFrame.getInstance().addPeer(peers[ctr]);
			Peers.getInstance().addPeer(peers[ctr], generateRandomColor());
		}
	}
	
    private static Color generateRandomColor() {
        int r = ThreadLocalRandom.current().nextInt(100, 256);
        int g = ThreadLocalRandom.current().nextInt(100, 256);
        int b = ThreadLocalRandom.current().nextInt(100, 256);
        return new Color(r, g, b);
    }
	
	public ObjectInputStream getObjectInputStream() {
		System.out.println("[ServerHandler] getObjectInputStream() input stream retrieved.");
		return server_response;
	}
}
