*/
	

citation
https://www.youtube.com/watch?v=vJ3jAaKPFIU&list=PL27BCE863B6A864E3&index=40

*/

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Server extends JFrame {

	private JTextField userData;
	private JTextArea DATA_INPUT;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	public Server(){
		super("Server");
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
		add(userData,BorderLayout.NORTH);
		DATA_INPUT = new JTextArea();
		add(new JScrollPane(DATA_INPUT));
		setSize(300,150);
		setVisible(true);
		
		}		

	
	public void start(){
		try{
			server = new ServerSocket(6789,100);
			while(true){
				
				try{
					waitForClient();
					Stream();
					whileConected();
					
				}catch(EOFException eofException){
					
				showbook("\n Server endded conection !");
			}finally{
				closeServer();
			}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
			
		}
		
	}
	
	private void waitForClient()throws IOException{
		
		showbook ("Waiting for someone to connect ..\n");
		connection = server.accept();
		showbook("now connected to " + connection.getInetAddress().getHostAddress());
		
		
	}
	
	//get stream to send and recieve data 
	
	private void Stream() throws IOException{
		
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showbook("\n Streams are now setup \n");
	}
	
	// during the conection
	
	private void whileConected() throws IOException{
		String book = " you are now connected";
		sendbook(book);
		abletoType(true);
		do{
			try{
				book = (String) input.readObject();
				showbook("\n"+ book);
			}catch(ClassNotFoundException classNotFoundException){
				showbook("\n idk wtf that you sent");
			}
			
			
		}while(!book.equals("CLIENT - END"));
		
	}
	
	// closeing 
	private void closeServer(){
		showbook("\n Closing connections .. \n");
		abletoType(false);
		try{
			output.close();
			input.close();
			connection.close();
			
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private void sendbook(String book){
		try {
			output.writeObject("SERVER - " + book);
			output.flush();
			showbook("\n SERVER - "+ book);
		}catch (IOException ioException){
			DATA_INPUT.append("\n EROOR: Cant send it bro");
		}
		
	}
	
	
	private void showbook(final String text){
		 SwingUtilities.invokeLater(
				 new Runnable(){
					 public void run(){
						 DATA_INPUT.append(text);
					 }
				 }
				 );
	}
	
	private void abletoType(final boolean tof){
		SwingUtilities.invokeLater(
				 new Runnable(){
					 public void run(){						 
						 userData.setEditable(tof);
					 }
				 }
				 );
		
	}
	
	}
	

