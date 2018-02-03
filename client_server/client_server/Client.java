import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {
	Socket Socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

	public Client(){
		
	}
	
	public void Connect(String ip, int port){
		
		try{
			Socket = new Socket(ip, port);
		}catch (ConnectException e){
			JOptionPane.showMessageDialog(null,  "no server available " , null, JOptionPane.ERROR_MESSAGE);
			
		}catch(UnknownHostException e){
			JOptionPane.showMessageDialog(null,  "Unknown Host" , null, JOptionPane.ERROR_MESSAGE);
			
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,  "Couldn't get I/O for the connection" , null, JOptionPane.ERROR_MESSAGE);
			
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
