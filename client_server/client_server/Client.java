/*
 * File: Client
 * Author: Troy Nechanicky, nech5860, 150405860 
 * 	Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: February 4, 2018
 * 
 * Description: Interface between GUI and server
 * 	Follows CP372 A1 Protocol RFC
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/* Relies on the following files to be in default package:
 * 	GUI
 * 	IsbnValidator
 */

import javax.swing.JOptionPane;

public class Client {
	final int reservedPort = 1023; 
	Socket socket;
	DataOutputStream out;
	BufferedReader in;

	Client() { }

	public static void main(String args[]) {
		new GUI(new Client());
	}

	public String Connect(String ip, int port){
		try {
			if (port > reservedPort) {
				socket = new Socket(ip, port);
				InputStream is = socket.getInputStream();
				out = new DataOutputStream(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(is));
				out.writeBytes("GET\n\n");
				return getResponse();
			}
			else {
				JOptionPane.showMessageDialog(null,  "Port numbers between 0 and 1,023 are reserved for privileged users" , null, JOptionPane.ERROR_MESSAGE);
			}
		} catch (ConnectException e){
			JOptionPane.showMessageDialog(null,  "No server available" , null, JOptionPane.ERROR_MESSAGE);

		} catch(UnknownHostException e){
			JOptionPane.showMessageDialog(null,  "Unknown Host" , null, JOptionPane.ERROR_MESSAGE);

		} catch(IOException e){
			JOptionPane.showMessageDialog(null,  "Couldn't get I/O for the connection" , null, JOptionPane.ERROR_MESSAGE);
		}

		return "";
	}

	public String sendRequest(String operation, String content) {
		String response = "";
		int isbnIndex, endIndex;
		boolean validIsbn;

		isbnIndex = content.toUpperCase().indexOf("ISBN");
	//	System.out.println("sending");

		if (isbnIndex != -1) {
			endIndex = content.indexOf('\n', isbnIndex);
			validIsbn = IsbnValidator.validIsbn(content.substring(isbnIndex, endIndex));
			if (!validIsbn) {
				response = "ERROR: Invalid ISBN";
				return response;
			}
		}
		content = content.trim();
		if (content.length() == 0) {
			return "ERROR: Invalid request";
		}
		content = operation + "\n" + content + "\n";
		//out.print(content);
		try {
			out.writeBytes(content);
		} catch (IOException e) {
			return "ERROR: Connection closed by client unexpectedly";
		}
		//System.out.println("cont: " + content);
		
		//System.out.println("getting r");
		response = getResponse();
		
		//System.out.println("Response: " + response);

		return response;
	}

	public String sendRequestBibtex(String operation, String content) {
		String response = "";

		content = content.trim();
		content = operation + "\n" + content + "\n\n";
		//System.out.println("hi");
		try {
			out.writeBytes(content);
		} catch (IOException e) {
			return "ERROR: Connection closed by client unexpectedly";
		}

		response = getResponse();

		return getBibtex(response);
	}

	private String getResponse() {
		String response = "", line;

		try {
			for (line = in.readLine(); line != null && !line.isEmpty(); line = in.readLine()) {		
			//	System.out.println("client: " + line);
				response += line;
			}
			if (line == null) {
				response = "ERROR: Connection closed by server unexpectedly";
			}
		} catch (IOException e) {
			response = "ERROR: Connection closed by server unexpectedly";
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private static String getBibtex(String response) {
		List<String> lines = new ArrayList<String>(Arrays.asList(response.split("\n")));
		String bibtex = "";

		for (int i = 0; i < lines.size(); i += 5) {
			bibtex = bibtex
					+ "@book{" + getKey(lines.subList(i, i + 5)) + ",\n"
					+ getBibtexLine(lines.get(i)) + ",\n"
					+ getBibtexLine(lines.get(i+1)) + ",\n"
					+ getBibtexLine(lines.get(i+2)) + ",\n"
					+ getBibtexLine(lines.get(i+3)) + ",\n"
					+ getBibtexLine(lines.get(i+4)) + "\n"
					+ "}\n";
		}

		return bibtex;
	}

	private static String getBibtexLine(String line) {	
		String[] splitLine = line.split(" ", 2);

		return splitLine[0].toLowerCase() + "={" + splitLine[1] + "}";
	}

	private static String getKey(List<String> lines) {
		int commaIndex = lines.get(2).substring(6).indexOf(',');
		if (commaIndex == -1) commaIndex = lines.get(2).length();	
		String lastName = lines.get(2).substring(7, commaIndex);
		lastName = String.format("%-4.4s", lastName).replace(' ', 'x');

		String year = String.format("%-4.4s", lines.get(4).substring(5)).replace(' ', 'x');

		return lastName + year;
	}

	public void closeConnection() throws IOException {
		//close streams and socket
		out.close();
		in.close();
		socket.close();

		return;
	}

}