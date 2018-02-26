import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class Sender extends Host {
	long startTime = System.nanoTime();
	int timeout;
	BufferedInputStream fileBuf;
	byte[] buf = new byte[124];
	SenderSocket socket;

	Sender(int localPort, int receiverPort, InetAddress receiverAddr, int timeout) throws SocketException {
		socket = new SenderSocket(localPort, receiverPort, receiverAddr);
		this.timeout = timeout;
	}

	public static void main(String[] args) {
		Sender sender;

		if (args.length != 5) {
			System.out.println("ERROR: Incorrect number of arguments");
			System.out.println("Expected: receiverIP receiverPort senderPort filename timeout");
			return;
		}
		try {
			sender = new Sender(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
					InetAddress.getByName(args[2]), Integer.parseInt(args[4]));
		}
		catch(Exception e) {
			printException(e, "ERROR: Invalid argument(s)");
			return;
		}

		try {
			sender.transfer(args[3]);
		}
		catch(FileNotFoundException e) {
			printException(e, "ERROR: Unable to open file");
		}
		catch(IOException e) {
			printException(e, "ERROR: Problem sending/receiving packet");
		}
		catch(Exception e) {
			printException(e);
		}

		sender.closeAll();

		return;
	}

	private void transfer(String filename) throws IOException, FileNotFoundException {
		fileBuf = new BufferedInputStream(new FileInputStream(filename));
		int bytesRead;
		boolean received = false;
		double transmissionTime;
		byte[] timeBytes = new byte[Double.BYTES];

		while (!received) {
			socket.receive();
			if (isSOT(socket.receivePacket.getData()[0])) received = true;
		}		

		socket.setSoTimeout(timeout / 1000);
		fileBuf.mark(buf.length+1);

		while ((bytesRead = fileBuf.read(buf, 0, buf.length)) != -1) {
			socket.send(buf, bytesRead);
			try {
				socket.receive();
			}
			catch(SocketTimeoutException e) {
				fileBuf.reset();
				continue;
			}
			if (isValidSeq(socket.receivePacket.getData()[0])) {
				//flip seqNum bit
				socket.seqNum = (byte) (socket.seqNum ^ 1);
			}
			else {
				fileBuf.reset();
			}
		}
		
		received = false;
		
		while (!received) {
			socket.sendEOT();
			try {
				socket.receive();
			}
			catch(SocketTimeoutException e) {
				continue;
			}
			if (isValidSeq(socket.receivePacket.getData()[0])) {
				//flip seqNum bit
				socket.seqNum = (byte) (socket.seqNum ^ 1);
				received = true;
			}
		}
		transmissionTime = ((double) (System.nanoTime() - startTime)) / 1E9;
		ByteBuffer.wrap(timeBytes).putDouble(transmissionTime);
				
		received = false;
		
		while (!received) {
			socket.send(timeBytes, Double.BYTES);
			try {
				socket.receive();
			}
			catch(SocketTimeoutException e) {
				continue;
			}
			if (isValidSeq(socket.receivePacket.getData()[0])) {
				//flip seqNum bit
				socket.seqNum = (byte) (socket.seqNum ^ 1);
				received = true;
			}
		}
		
		return;
	}

	private boolean isSOT(byte header) {
		return (header & Socket.SOTMask) == 1;
	}

	private void closeAll() {
		try {
			fileBuf.close();
		}
		catch (Exception e) {}

		return;		
	}

	private static void printException(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}

	private static void printException(Exception e, String msg) {
		System.out.println(msg);
		System.out.println(e.getMessage());
		e.printStackTrace();
	}

}