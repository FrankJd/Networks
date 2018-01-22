import javax.swing.JFrame;

public class ClientTest {
	public static void main(String[] args) {
		Client s;
		s = new Client("127.0.0.1");
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.start();
	}
}
