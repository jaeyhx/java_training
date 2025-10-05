package appframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import appclass.Authentication;
import appclass.Constants;
import appclass.Peer;
import appclass.Peers;
import appclass.User;
import server.ServerHandler;

public class ChatClientFrame {
	private JFrame clientFrame = new JFrame("KyoChat Client");
	private DefaultListModel<String> peerModel = new DefaultListModel<>();
	private static ChatClientFrame instance = new ChatClientFrame();
	//private JTextArea chatArea = new JTextArea();
	private JPanel left = new JPanel(new BorderLayout());
	private JPanel right = new JPanel(new BorderLayout());
	private JScrollPane scroll = new JScrollPane(right);
	private JPanel inputPanel = new JPanel(new BorderLayout());
	private JTextField input = new JTextField();
	private JList peers = new JList(peerModel);
	private JPanel peer_msg = new JPanel();
	private JButton send = new JButton("Send");
	private JSplitPane display = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left,right);
	private JButton burgerButton = new JButton("☰");
	private JPopupMenu popup = new JPopupMenu();
	private JMenuItem home = new JMenuItem("Profile");
	private JMenuItem settings = new JMenuItem("Settings");
	private JMenuItem logout = new JMenuItem("Logout");
	
	private ChatClientFrame() {
		initialize();
	}
	
	public static ChatClientFrame getInstance() {
		return instance;
	}

	private void initialize() {
		System.out.println("[ChatClientFrame] initialize() creating chat UI.");
		clientFrame.setMinimumSize(new Dimension(800,600));
		clientFrame.setSize(800,600);
		
		send.setMinimumSize(new Dimension(100,50));
		send.setPreferredSize(new Dimension(100,50));
		
		input.setMinimumSize(new Dimension(500,50));
		input.setPreferredSize(new Dimension(500,50));
	    
		peer_msg.setLayout(new GridLayout(0,1));
	    scroll = new JScrollPane(peer_msg);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
        burgerButton.setMinimumSize(new Dimension(50, 50));
        burgerButton.setPreferredSize(new Dimension(50, 50));

        popup.add(home);
        popup.add(settings);
        popup.add(logout);

        left.add(burgerButton, BorderLayout.PAGE_START);
        left.add(peers,BorderLayout.CENTER);
		
		inputPanel.setMinimumSize(new Dimension(600,100));
		inputPanel.add(input, BorderLayout.CENTER);
		inputPanel.add(send, BorderLayout.EAST);

		right.setLayout(new BorderLayout());
		right.add(scroll, BorderLayout.CENTER);
		right.add(inputPanel, BorderLayout.SOUTH);
		
		display.setAutoscrolls(true);
		display.setDividerSize(1);
		display.setDividerLocation(200);

		clientFrame.add(display);
		
		menuPressListener();
		logoutPressListener();
		EnterKeyPressListener();
		SendButtonPressListener();
	}
	
	private void menuPressListener() {
	       burgerButton.addActionListener(e -> {
	            popup.show(burgerButton, 0, burgerButton.getHeight());
	        });
	}
	
	private void logoutPressListener() {
		logout.addActionListener(e -> {
		    try {
		    	
		    	Authentication auth = new Authentication();

		        System.out.println("[ChatClientFrame] logoutPressListener() Logout request sent to server.");
		    	boolean ret = auth.logout(User.getInstance().getUser());
		    	
		    	if(true == ret) {
		    		User.getInstance().setUser("");
		    	}

		    } catch (Exception ex) {
		        System.out.println("[ChatClientFrame] logoutPressListener() Logout failed: " + ex.getMessage());
		    }
		});

	}
	
	public void setVisible(boolean value) {
		clientFrame.setVisible(value);
	}
	
	public void setEnabled(boolean value) {
		clientFrame.setEnabled(value);
	}
	
	public void addPeer(String name) {
		peerModel.addElement(name);
	}
	
	public void removePeer(String name) {
		peerModel.removeElement(name);
	}
	
	/*public void updatePeerListUI(String peerlist) {
		System.out.println("[ChatClientFrame] updatePeerListUI() updating...");
			peerModel.addElement(peerlist);
	}*/

	private void EnterKeyPressListener() {
		System.out.println("[ChatClientFrame] EnterKeyPressListener() initiated...");
	    input.addActionListener(e -> {
	        String message = input.getText().trim();
	        if (!message.isEmpty()) {
	    		System.out.println("[ChatClientFrame] EnterKeyPressListener() Sending message to server.");
	    		ServerHandler.getInstance().serverRequest(Constants.CMD_MSG, message);
	            input.setText("");
	        }
	    });
	}
	
	private void SendButtonPressListener() {
		System.out.println("[ChatClientFrame] SendButtonPressListener() initiated...");
		send.addActionListener(e -> {
	        String message = input.getText().trim();
	        if (!message.isEmpty()) {
	    		System.out.println("[ChatClientFrame] SendButtonPressListener() Sending message to server.");
	    		ServerHandler.getInstance().serverRequest(Constants.CMD_MSG, message);
	            input.setText("");
	        }
	    });
	}
	
	public void UpdateChat(String name, Color color, String message) {
	JPanel row = new JPanel(new BorderLayout());
    row.setPreferredSize(new Dimension(100, 50));
    row.setMaximumSize(new Dimension(100, 50));
    row.setMinimumSize(new Dimension(100, 50));

    JTextArea msgText = new JTextArea(message);
    msgText.setLineWrap(true);
    msgText.setWrapStyleWord(true);
    msgText.setEditable(false);
    msgText.setBackground(color);
    msgText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // Wrap the message in a bubble panel
    JPanel bubble = new JPanel(new BorderLayout());
    bubble.add(msgText, BorderLayout.CENTER);

    if (name.equals(User.getInstance().getUser())) {
        row.add(bubble, BorderLayout.EAST); // align right
    } else {
        row.add(bubble, BorderLayout.WEST); // align left
    }

    peer_msg.add(row);
    peer_msg.revalidate();
    peer_msg.repaint();

    JScrollBar vertical = scroll.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());
	}
}
