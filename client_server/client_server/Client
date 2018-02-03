import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class Client {
	final int reservedPort = 1023; 
	Socket Socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    private ObjectOutputStream output;

	public Client(){
		
	}
	
	public void Connect(String ip, int port){
		try{
			if (port  > reservedPort)
			Socket = new Socket(ip, port);
			else{
				JOptionPane.showMessageDialog(null,  "Port numbers between 0 and 1,023 are reserved for privileged users " , null, JOptionPane.ERROR_MESSAGE);
			}
		}catch (ConnectException e){
			JOptionPane.showMessageDialog(null,  "no server available " , null, JOptionPane.ERROR_MESSAGE);
			
		}catch(UnknownHostException e){
			JOptionPane.showMessageDialog(null,  "Unknown Host" , null, JOptionPane.ERROR_MESSAGE);
			
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,  "Couldn't get I/O for the connection" , null, JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	public void Submit(String book){
		try{
			output.writeObject(book);
			output.flush();
			
		}catch(IOException ioException){
			JOptionPane.showMessageDialog(null,  "Something went wrong!" , null, JOptionPane.ERROR_MESSAGE);
		}
	}
	public void closeConnection(){
		try{
			Socket.close();
			out.close();
			in.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
}
