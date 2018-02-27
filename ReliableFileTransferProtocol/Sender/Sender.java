import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class Sender extends Host {
	private long startTime;
	private int timeout;
	private BufferedInputStream fileBuf;
	private byte[] buf = new byte[124];

	Sender(int localPort, int receiverPort, InetAddress receiverAddr, int timeout) throws SocketException {
		socket = new SenderSocket(localPort, receiverPort, receiverAddr);
		this.timeout = timeout;
	}

	public static void main(String[] args) {
		Sender sender;
		double elapsedTime;

		if (args.length != 5) {
			System.out.println("ERROR: Incorrect number of arguments");
			System.out.println("Expected: receiverIP receiverPort senderPort filename timeout");
			return;
		}
		try {
			sender = new Sender(Integer.parseInt(args[2]), Integer.parseInt(args[1]), InetAddress.getByName(args[0]), Integer.parseInt(args[4]));
		}
		catch(Exception e) {
			printException(e, "ERROR: Invalid argument(s)");
			return;
		}

		try {
			elapsedTime = sender.transfer(args[3]);
			System.out.println("SUCCESS: File transferred in "
					+ String.format("%.4f", elapsedTime) + " milliseconds");
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

	private double transfer(String filename) throws IOException, FileNotFoundException {
		fileBuf = new BufferedInputStream(new FileInputStream(filename));
		int bytesRead;
		boolean received = false;
		double transmissionTime;
		byte[] timeBytes = new byte[Double.BYTES];
		int timeoutMilli = Math.max(1, timeout / 1000);
		long sent = 0;

		while (!received) {
			socket.receive();
			if (isSOT(socket.receivePacket.getData()[0])) received = true;
		}		
		
		socket.setSoTimeout(timeoutMilli);
		fileBuf.mark(buf.length+1);
		startTime = System.nanoTime();

		while ((bytesRead = fileBuf.read(buf, 0, buf.length)) != -1) {
			socket.send(buf, bytesRead);
			sent += bytesRead;
			try {
				socket.receive();
			}
			catch(SocketTimeoutException e) {
				fileBuf.reset();
				continue;
			}
			if (isValidSeq(socket.receivePacket.getData()[0])) {
				nextSeq();
				fileBuf.mark(buf.length+1);
			}
			else {
				fileBuf.reset();
			}
		}

		received = false;

		while (!received) {
			((SenderSocket) socket).sendEOT();
			try {
				socket.receive();
			}
			catch(SocketTimeoutException e) {
				continue;
			}
			if (isValidSeq(socket.receivePacket.getData()[0])) {
				nextSeq();
				received = true;
			}
		}
		transmissionTime = ((double) (System.nanoTime() - startTime)) / 1E9;

		/*ByteBuffer.wrap(timeBytes).putDouble(transmissionTime);

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
				nextSeq();
				received = true;
			}
		}*/

		return transmissionTime;
	}

	private boolean isSOT(byte header) {
		return (header & Socket.SOTMask) == Socket.SOTMask;
	}

	private void closeAll() {
		try {
			fileBuf.close();
			socket.close();
		}
		catch (Exception e) {}

		return;		
	}
}