package app;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class ServerListener extends Thread {
	private Socket server;
	private ObjectInputStream server_message;

	public ServerListener(Socket server) {
		System.out.println("[KyoChat] ServerListener() initializing server listener.");
		this.server = server;
	}
	
	@Override
	public void run() {
        try {
            while (true) {
        		System.out.println("[ServerListener] ServerListener() listening to server.");
        		server_message = new ObjectInputStream(server.getInputStream());
                HashMap<String,String> in = (HashMap<String,String>) server_message.readObject();
        		System.out.println("[ServerListener] ServerListener() server reply received.");
                if (in instanceof HashMap) {
            		System.out.println("[ServerListener] ServerListener() reading reply from server.");
                    HashMap<String, String> map = in;
            		System.out.println("[ServerListener] ServerListener() interpreting.");
            		System.out.println("[ServerListener] ServerListener() server reply: " + in.toString());
            		ServerResponseBuilder.getInstance().ResponseExtractor(map);
            		ServerHandler.getInstance().ReceiveMessage(ServerResponseBuilder.getInstance().getCommand());
                }
            }
        } catch (Exception e) {
    		System.out.println("[ServerListener] ServerListener() ERROR! " + e.getMessage());
        }

	}
}
