/*
 * Author: Troy Nechanicky, nech5860, 150405860 
 * 	Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: March 6, 2018
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/* Relies on the following files to be in default package:
 * 	GUI
 * 	Host
 *  ReceiverSocket
 */

import javax.swing.JOptionPane;

public class Receiver extends Host implements Runnable {
	private boolean reliable;
	private FileOutputStream fileOutput;
	GUI gui;

	Receiver(GUI gui, int receiverPort, int senderPort, String senderAddr, boolean reliable, String filename) throws Exception {
		this.reliable = reliable; 
		this.gui = gui;

		try {
			socket = new ReceiverSocket(receiverPort, senderPort, InetAddress.getByName(senderAddr));
			fileOutput = new FileOutputStream(filename);
		} catch (SocketException | UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "ERROR: Invalid connection parameters", null, JOptionPane.ERROR_MESSAGE);
			printException(e, "ERROR: Invalid parameters");
			throw e;
		} catch (IOException e) {
			closeAll();
			JOptionPane.showMessageDialog(null, "ERROR: Invalid filename", null, JOptionPane.ERROR_MESSAGE);
			printException(e, "ERROR: Invalid filename");
			throw e;
		} catch (Exception e) {
			closeAll();
			JOptionPane.showMessageDialog(null, "ERROR: Unexpected exception", null, JOptionPane.ERROR_MESSAGE);
			printException(e);
			throw e;
		}
	}

	public static void main(String args[]) {
		new GUI();
	}

	public void run() {
		try {
			getFile();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: Unexpected exception", null, JOptionPane.ERROR_MESSAGE);
			printException(e);
			closeAll();
			return;
		}

		closeAll();

		JOptionPane.showMessageDialog(null, "SUCCESS: Transfer completed", null, JOptionPane.PLAIN_MESSAGE);

		gui.shit();

		return;
	}

	private void getFile() throws IOException {
		boolean received = false;
		boolean EOT = false;
		byte[] buf;
		int bufLen;

		//resend SOT after 0.5 sec in case lost
		socket.setSoTimeout(500);

		while (!received) {
			((ReceiverSocket) socket).sendSOT();

			try {
				((ReceiverSocket) socket).receive(reliable);
			}
			catch(SocketTimeoutException e) {
				continue;
			}

			buf = socket.receivePacket.getData();
			bufLen = socket.receivePacket.getLength();

			if (isValidSeq(buf[0])) {
				((ReceiverSocket) socket).sendACK();
				gui.displayPacketCount(((ReceiverSocket) socket).getReceivedCount());

				if (isEOT(buf[0])) {
					EOT = true;
				}
				else {
					fileOutput.write(buf, 1, bufLen-1);
				}

				received = true;
				nextSeq();	
			} else {
				((ReceiverSocket) socket).sendACK();
			}
		}
		
		//remove timeout
		socket.setSoTimeout(0);

		while (!EOT) {
			((ReceiverSocket) socket).receive(reliable);
			buf = socket.receivePacket.getData();
			bufLen = socket.receivePacket.getLength();

			if (isValidSeq(buf[0])) {
				((ReceiverSocket) socket).sendACK();
				gui.displayPacketCount(((ReceiverSocket) socket).getReceivedCount());

				if (isEOT(buf[0])) {
					EOT = true;
				}
				else {
					fileOutput.write(buf, 1, bufLen-1);
				}
			} else {
				nextSeq();
				((ReceiverSocket) socket).sendACK();
			}

			nextSeq();			
		}

		//wait 1 second to make sure sender received ack (and thus doesn't resend EOT)
		socket.setSoTimeout(1000);
		received = false;

		while (!received) {
			try {
				((ReceiverSocket) socket).receive(reliable);
			}
			catch(SocketTimeoutException e) {
				received = true;
			}

			((ReceiverSocket) socket).sendACK();
		}

		return;	
	}

	private boolean isEOT(byte header) {
		return (header & Socket.EOTMask) == Socket.EOTMask;
	}

	private void closeAll() {
		try {
			fileOutput.close();
			socket.close();
		}
		catch (Exception e) {}

		return;		

	}
}