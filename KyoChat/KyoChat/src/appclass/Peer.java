package appclass;

import java.awt.Color;

public class Peer {
    private String peername;
    private Color color;
    //private String timestamp;

    /*public Peer(String peer, Color color, String timestamp) {
        this.peer = peer;
        this.color = color;
        this.timestamp = timestamp;
    }*/
    public Peer(String peername, Color color) {
    	System.out.println("[Peer] Peer() name=" + peername + " color=" + color);
        this.peername = peername;
        this.color = color;
    }

    public String getPeer() {
        return peername;
    }

    public void setPeer(String peername) {
        this.peername = peername;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    /*public String getTimestamp() {
    	return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
    	this.timestamp = timestamp;
    }*/
}