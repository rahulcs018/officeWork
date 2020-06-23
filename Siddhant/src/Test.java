import java.util.ArrayList;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) {
		ArrayList arr = new ArrayList<>();
		arr.add(1);
		arr.add(2);

		Iterator it = arr.iterator();
		while (it.hasNext()) {
			int ab = (int) it.next();
			System.out.println(ab);
		}
	}
}
