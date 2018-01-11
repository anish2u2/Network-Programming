
public class MainEX {

	public static void main(String args[]) {
		int[] arr = { 1, 2, 3, 4 };
		int[] brr = { 5, 6, 7, 8 };
		System.arraycopy(arr, 0, brr, brr.length, arr.length);
		System.out.println(brr);
	}

}
