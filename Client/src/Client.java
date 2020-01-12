import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Client extends JFrame {
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor 
	public Client(String host) {
		super("Client");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
				);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(300,150);
		setVisible(true);
		 
	}
	//connect to server
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
			close();
		}catch(EOFException eofException) {
			showMessage("\n Client terminated  connection");
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}finally {
			close();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP),6789);
		showMessage("You are connected to: " + connection.getInetAddress().getHostName());
		
	}
	
	//set up streams to send and receieve messages
	private void setupStreams()throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Good to go");
	}
	private void whileChatting() throws IOException {
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n "+message);
			}catch(ClassNotFoundException e) {
				showMessage("\n I do not know that object type");
			}
		}while(!message.equals("END"));
}
	//send messages to the sever
	private void sendMessage(String message) {
		try {
			output.writeObject("CLIENT - " + message);
			output.flush();
			showMessage("\nCLIENT - " + message);
	}catch(IOException ioException) {
		chatWindow.append("\n ERROR: MESSAGE CAN'T BE SENT");
	}
		
	}	
	//close the streams and sockets
	private void close() {
		showMessage("\n Closing connections...     \n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	//updates chatWindow
		public void showMessage(final String text) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					chatWindow.append(text);
				}
			});
		}
		
		//let the user type
		private void ableToType(final boolean tof) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					userText.setEditable(tof);
				}
			});
			
		}
		
}
