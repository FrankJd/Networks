*/
	

citation
https://www.youtube.com/watch?v=vJ3jAaKPFIU&list=PL27BCE863B6A864E3&index=40

*/
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame{
	
	private JTextField userData;
	private JTextArea DATA_INPUT;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String book = "";
	private String serverIP;
	private Socket connection;
	
	
	public Client(String host){
		super("Client");
		serverIP = host;
		userData = new JTextField();
		userData.setEditable(false);
		userData.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendbook(event.getActionCommand());
					userData.setText("");
				}
			}
		);
		add(userData, BorderLayout.NORTH);
		DATA_INPUT = new JTextArea();
		add(new JScrollPane(DATA_INPUT));
		setSize(300, 150); 
		setVisible(true);
	}
	
	//connect to server
	public void start(){
		try{
			connect();
			Stream();
			whilecomuncting();
		}catch(EOFException eofException){
			showbook("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	//connect to server
	private void connect() throws IOException{
		showbook("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showbook("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
	}
	
	//set up streams
	private void Stream() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showbook("\n The streams are now set up! \n");
	}
	
	//while chatting with server
	private void whilecomuncting() throws IOException{
		ableToType(true);
		do{
			try{
				book = (String) input.readObject();
				showbook("\n" + book);
			}catch(ClassNotFoundException classNotFoundException){
				showbook("Unknown data received!");
			}
		}while(!book.equals("SERVER - END"));	
	}
	
	//Close connection
	private void closeConnection(){
		showbook("\n Closing the connection!");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//send book to server
	private void sendbook(String book){
		try{
			output.writeObject("CLIENT - " + book);
			output.flush();
			showbook("\nCLIENT - " + book);
		}catch(IOException ioException){
			DATA_INPUT.append("\n Oops! Something went wrong!");
		}
	}
	
	//update chat window
	private void showbook(final String book){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					DATA_INPUT.append(book);
				}
			}
		);
	}
	
	//allows user to type
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userData.setEditable(tof);
				}
			}
		);
	}
}
