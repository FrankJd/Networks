/*
 * File: Server
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Group: 08
 * Version: February 4, 2018
 * 
 * Description: Server that monitors connection requests
 * 	Spawns ClientHandler thread to handle a connection
 * 	Must be manually terminated, following example code given by Dr. Zima
 */

import java.net.ServerSocket;
import java.net.Socket;
/* Relies on the following files to be in default package:
 *	ClientHandler
 */

public class Server {
	public static void main(String argv[]) throws Exception {
		int portNumber = new Integer(argv[0]);
		
		//server never closes cleanly, must be manually terminated
		//so surpress warning about not closing socket in main
		@SuppressWarnings("resource")
		//will throw exception if portNumber is invalid
		ServerSocket socket = new ServerSocket(portNumber);

		//continuously listen for connection requests
		//create thread for each request
		while (true) {
			//listen and accept connection request
			Socket connection = socket.accept();
			ClientHandler request = new ClientHandler(connection);
			Thread thread = new Thread(request);
			thread.start();
		}
	}
}
