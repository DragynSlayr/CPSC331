package map;

import java.util.HashMap;
import java.util.Iterator;

public class MapTest {

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("First Name", "Inderpreet");
		map.put("Last Name", "Dhillon");
		map.put("Age", "20");

		Iterator<String> selector = map.keySet().iterator();

		while (selector.hasNext()) {
			String key = selector.next();
			System.out.printf("%s: %s\n", key, map.get(key));
		}
	}

}
