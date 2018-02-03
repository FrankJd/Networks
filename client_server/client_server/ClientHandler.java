/*
 * File: ClientHandler
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Group: 08
 * Version: February 4, 2018
 * 
 * Description:
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* Relies on the following files to be in default package:
 * 	RequestException
 * 	Book
 * 	Server
 */

public class ClientHandler implements Runnable {

	public enum Field { ISBN, TITLE, AUTHOR, YEAR, PUBLISHER }; //possible fields in client request
	private Map<Field, String> requestContent = new HashMap<Field, String>(5); //used to store content of client request. Values null by default
	private static Bibliography bib = new Bibliography(); //list of books submitted by clients (1 list shared by all server threads)
	private Socket socket;

	/*set in setUpConnection*/
	private BufferedReader bufferedReader; //use to read from socket
	private DataOutputStream outputStream; //use to write to socket

	public ClientHandler(Socket socket) {
		this.socket = socket;

		//intialize requestContent keys, set values to null
		requestContent.put(Field.ISBN, null);
		requestContent.put(Field.TITLE, null);
		requestContent.put(Field.AUTHOR, null);
		requestContent.put(Field.YEAR, null);
		requestContent.put(Field.PUBLISHER, null);
	}

	@Override
	public void run() {
		try {
			setUpConnection();
			processRequests();
		} catch (RequestException reqException) {
			try {
				sendMsg(reqException.getMessage());
			} 
			//bad connection, print error to console
			catch (Exception connectionException) {
				System.out.print(connectionException.getMessage());
				connectionException.printStackTrace();
			}
		}
		catch (Exception generalException) {
			//try to send error to client
			try {
				sendException(generalException);
			}
			//bad connection, print error to console
			catch (Exception connectionException) {
				System.out.println("Unable to send exception to client on port " + socket.getLocalPort());
				System.out.println(connectionException.getMessage());
				connectionException.printStackTrace();
			}
		}		
	}

	//sets up buffer and streams
	private void setUpConnection() throws IOException {
		InputStream is = socket.getInputStream();
		outputStream = new DataOutputStream(socket.getOutputStream());
		bufferedReader = new BufferedReader(new InputStreamReader(is));

		return;
	}

	/*
	 * NEED TO ADD HANDLING IF CLIENT CLOSES CONNECTION
	 * NEED TO CHECK WHAT BUFFEREDREADER.READY() RETURNS IF CONNECTION CLOSED
	 */
	//process client requests until client closes connection
	private void processRequests() throws RequestException, IOException {
		String requestType; //first line of request, expected to be one of SUBMIT, UPDATE, GET, REMOVE
		String contentLine; //line of request content
		boolean isAll = false; //used to track if ALL option in request
		List<String> response = null; //each element is a line to send in response

		sendMsg("SUCCESS: Conection accepted\n");

		//endless loop to process requests
		while (true) {
			requestType = readLine();

			//get first line of request content
			contentLine = readLine();

			if (contentLine.toUpperCase().equals("ALL")) {
				//check to make sure nothing else in request, else invalid request
				if (readLine().length() != 0) {
					throw new RequestException("ERROR: Invalid request");
				}

				isAll = true;
			}
			//get all request content
			//and validate isbn, if given
			else {
				handleRequestLine(contentLine);

				contentLine = readLine();

				//get remaining request content
				while (contentLine.length() != 0) {
					handleRequestLine(contentLine);
					contentLine = readLine();
				}
			}

			switch (requestType.toUpperCase()) {
			case "SUBMIT":
				response = bib.add(requestContent);
				break;
			case "UPDATE":
				response = bib.update(requestContent);
				break;
			case "GET":
				response = isAll ? bib.getAll() : bib.get(requestContent);
				break;
			case "REMOVE":
				response = isAll ? bib.getAll() : bib.get(requestContent);
				sendMsg("Please confirm the removal of " + response.size() + " record(s) (Y/N)");
				contentLine = readLine();

				if (contentLine.toUpperCase().equals("Y")) {
					response = isAll ? bib.removeAll() : bib.remove(requestContent);
				} else if (contentLine.toUpperCase().equals("Y")) {
					response = Arrays.asList("SUCCESS: Remove cancelled");
				} else {
					throw new RequestException("ERROR: Invalid Request");
				}

				break;
			case "CLOSE":
				closeConnection();
				break;
			default:
				throw new RequestException("ERROR: Invalid operation " + requestType);
			}

			sendMsg(response);

			//clear request content
			requestContent.replaceAll((k,v) -> "");
		}
	}

	private String readLine() throws RequestException, IOException {
		long start = System.nanoTime();

		while (!bufferedReader.ready()) { 
			if (System.nanoTime() - start > 5E9) {
				throw new RequestException("ERROR: Request timed-out after 5 seconds");
			}
		}

		return bufferedReader.readLine().trim();
	}

	private void handleRequestLine(String contentLine) throws RequestException {
		Field field; //field from contentLineSplit[0]		
		String[] contentLineSplit; //line of request content split at first space, expected first element like one of Field type, second element value of field

		contentLineSplit = contentLine.split(" ", 1); //split by first space, first element is Field, 2nd field value

		//try to convert given field string into Field type
		try {
			field = Field.valueOf(contentLineSplit[0].trim());
		}
		catch (java.lang.IllegalArgumentException e) {
			throw new RequestException("ERROR: Invalid field " + contentLineSplit[0]);
		}

		//check to make sure value given for field
		if (contentLineSplit.length != 2) {
			throw new RequestException("ERROR: Field " + contentLineSplit[0] + " missing value");
		}

		//store value for field
		requestContent.replace(field, contentLineSplit[1]);

		return;
	}

	private void closeConnection() throws IOException {
		//close streams and socket
		outputStream.close();
		bufferedReader.close();
		socket.close();

		return;
	}

	private void sendException(Exception e) {

	}

	private void sendMsg(String msg) throws IOException {
		outputStream.writeBytes(msg);

		return;
	}

	private void sendMsg(List<String> msg) throws IOException {
		for (String line : msg) {
			sendMsg(line);
		}

		return;
	}

}
