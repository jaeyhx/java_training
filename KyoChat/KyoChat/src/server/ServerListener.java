package server;

import java.io.ObjectInputStream;
import java.util.HashMap;

public class ServerListener extends Thread {
	private ObjectInputStream server_message;
	private  boolean server_listen = true;

	public ServerListener() {
		System.out.println("[KyoChat] ServerListener() initializing server listener.");
	}
	
	@Override
	public void run() {
		 server_message = ServerHandler.getInstance().getObjectInputStream();
        try {
            while (server_listen) {
        		System.out.println("[ServerListener] ServerListener() listening to server.");
                HashMap<String,String> in = (HashMap<String,String>) server_message.readObject();
        		System.out.println("[ServerListener] ServerListener() server reply received.");
                if (in instanceof HashMap) {
            		System.out.println("[ServerListener] ServerListener() reading reply from server.");
                    HashMap<String, String> map = in;
            		System.out.println("[ServerListener] ServerListener() server reply: " + in.toString());
            		ServerResponseBuilder.getInstance().ResponseExtractor(map);
            		ServerHandler.getInstance().ReceiveMessage(ServerResponseBuilder.getInstance().getCommand());
                }
            }
    		System.out.println("[ServerListener] ServerListener() stop thread.");
        } catch (Exception e) {
    		System.out.println("[ServerListener] ServerListener() ERROR! " + e.getMessage());
        }
	}
	
	public void stopThread() {
		server_listen = false;
	}
}
