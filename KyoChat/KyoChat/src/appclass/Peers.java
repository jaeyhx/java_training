package appclass;

import java.awt.Color;
import java.util.HashMap;

public class Peers {

	private static Peers instance = new Peers();
	private static HashMap<String,Color> peers = new HashMap<String,Color>();

	public Peers() {
	}
	
	public static Peers getInstance() {
		return instance;
	}

	public void wipePeers() {
		peers = null;
	}
	
	public int getPeersCount() {
		return peers.size();
	}
	
	public HashMap<String,Color> getPeers() {
		return peers;
	}
	
	public void addPeer(String name, Color color) {
		//peers = 
		peers.put(name, color);
	}
	
	public void removePeer(String name) {
		peers.remove(name);
	}
}
