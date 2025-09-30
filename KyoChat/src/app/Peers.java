package app;

import java.util.LinkedList;

public class Peers {


	private static Peers instance = new Peers();
	private static LinkedList<Peer> peers = new LinkedList<>();

	public Peers() {
	}
	
	public static Peers getInstance() {
		return instance;
	}
	
	public void addPeer(String name) {
		peers.add(new Peer(name));
	}
	
	public void removePeer(String name) {
		peers.remove(new Peer(name));
	}
	
	public void wipePeers() {
		peers = new LinkedList<>();
	}
	
	public int getPeersCount() {
		return peers.size();
	}
	
	public LinkedList<Peer> getPeers() {
		return peers;
	}
	
}
