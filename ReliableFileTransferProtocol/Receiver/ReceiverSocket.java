import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class ReceiverSocket extends Socket {
	private int receivedPackets = 0;

	ReceiverSocket(int localPort, int receiverPort, InetAddress receiverAddr) throws SocketException {
		super(localPort, receiverPort, receiverAddr);
	}
	
	public int getReceivedCount() {
		return receivedPackets;
	}

	void receive(boolean reliable) throws IOException {
		super.receive();
		receivedPackets++;
		
		//"drop" every 10th packet
		if (!reliable && (receivedPackets % 10 == 0)) receive(receivePacket);

		return;
	}

	void sendACK() throws IOException {
		sendBuf[0] = seqNum;
		sendPacket.setLength(1);

		send(sendPacket);

		return;
	}

	void sendSOT() throws IOException {
		sendBuf[0] = (byte) (SOTMask + seqNum);
		sendPacket.setLength(1);

		send(sendPacket);
	}
}