package search;

public class BinarySearch {

	public static int search(int[] a, int key) {
		if (a.length == 0) {
			return -1;
		} else {
			int low = 0;
			int high = a.length - 1;
			while (low <= high) {
				int mid = (low + high) / 2;
				if (key > a[mid]) {
					low = mid + 1;
				} else if (key < a[mid]) {
					high = mid - 1;
				} else {
					return mid;
				}
			}
			return -1;
		}
	}

	public static int searchRecursive(int[] a, int key, int low, int high) {
		if (low > high) {
			return -1;
		} else {
			int mid = (low + high) / 2;
			if (a[mid] < key) {
				return searchRecursive(a, key, low, mid - 1);
			} else if (a[mid] > key) {
				return searchRecursive(a, key, mid + 1, high);
			} else {
				return mid;
			}
		}
	}
}
