import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class SenderSocket extends Socket {

	SenderSocket(int localPort, int receiverPort, InetAddress receiverAddr) throws SocketException {
		super(localPort, receiverPort, receiverAddr);
	}

	//signal End Of Transmission bit  
	void sendEOT() throws IOException {
		sendBuf[0] = (byte) (EOTMask + seqNum);
		sendPacket.setLength(1);

		send(sendPacket);

		return;
	}
}
