import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import InvalidFieldException;

public class t {
	public static void main(String[] args) {
		String str = "g\n\n\n\n\n\n\n\n\n\n\n\ng";
		int count1 = str.length();
		
		str = str.replaceAll("\n\n\n", "\n");
		str = str.replaceAll("\n\n", "\n");
		str = str.replaceAll("\n\n", "\n");
		
		System.out.print(str);
	}
}
