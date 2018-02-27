import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReceiverDriver {

	public static void main(String[] args) throws UnknownHostException {
		Receiver receiver = new Receiver(800, 8080, "127.0.0.1", false, "recFile.jpg");
		
		receiver.transfer();
	}

}
