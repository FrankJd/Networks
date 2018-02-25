package t;

public class t {

	public static void main(String [] args) {
		Integer[] arr = new Integer[1];
		Integer[] arr2 = new Integer[2];
		Integer b = 5;

		arr[0] = 2;
		arr2[0] = b;
		
		System.arraycopy(arr, 0, arr2, 1, arr.length);

		System.out.print(arr2[0]);
		System.out.print(arr2[1]);
	}
}
