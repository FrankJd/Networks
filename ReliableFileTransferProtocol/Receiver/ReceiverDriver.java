import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReceiverDriver {

	public static void main(String[] args) throws UnknownHostException {
		Receiver receiver = new Receiver();
		
		receiver.transfer(800, 8080, InetAddress.getByName("127.0.0.1"), true, "recFile");
	}

}
