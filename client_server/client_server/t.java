import java.util.HashMap;
import java.util.Map;

public class t {

	public static void main(String[] args) {
		Map test = new HashMap();
		
		test.put(0, null);
		test.put(1, 2);
		test.put(1, 3);
		System.out.print(test.values());

	}

}
