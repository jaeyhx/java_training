package app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClientFrame {
	private JFrame clientFrame = new JFrame("KyoChat Client");
	private DefaultListModel<String> peerModel = new DefaultListModel<>();
	private static ChatClientFrame instance = new ChatClientFrame();
	private JTextArea chatArea = new JTextArea();
	private JPanel left = new JPanel(new BorderLayout());
	private JPanel right = new JPanel(new BorderLayout());
	private JPanel inputPanel = new JPanel(new BorderLayout());
	private JTextField input = new JTextField();
	private JList peers = new JList(peerModel);
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
		
		chatArea.setMinimumSize(new Dimension(600,500));
		chatArea.setPreferredSize(new Dimension(600,500));
		chatArea.setEditable(false);
		
        burgerButton.setMinimumSize(new Dimension(50, 50));
        burgerButton.setPreferredSize(new Dimension(50, 50));

        popup.add(home);
        popup.add(settings);
        popup.add(logout);

        left.add(burgerButton, BorderLayout.PAGE_START);
		
		inputPanel.setMinimumSize(new Dimension(600,100));
		inputPanel.add(input, BorderLayout.CENTER);
		inputPanel.add(send, BorderLayout.EAST);
		
		right.add(chatArea);
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
	
	public void updatePeers() {
		for(int ctr = 0; ctr < Peers.getInstance().getPeersCount();ctr++) {
			peerModel.addElement(((Peer) Peers.getInstance().getPeers().get(ctr)).getPeer());
		}
	}
	

	private void EnterKeyPressListener() {
		System.out.println("[ChatClientFrame] EnterKeyPressListener() initiated...");
	    input.addActionListener(e -> {
	        String message = input.getText().trim();
	        if (!message.isEmpty()) {
	    		System.out.println("[ChatClientFrame] EnterKeyPressListener() Sending message to server.");
	    		ServerHandler.getInstance().SendMessage(message);
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
	    		ServerHandler.getInstance().SendMessage(message);
	            input.setText("");
	        }
	    });
	}
	
	public void UpdateChat(String message) {
		chatArea.append(message);
	}
	
}
