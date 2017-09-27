/* Author: Jason Morgan
 * Date: February 4, 2017
 * Purpose: This program is designed to run the 
 * Selection Sort algorithm.
 */

package benchmarkSort;

import java.io.IOException;

public interface SortInterface {
	int[] iterativeSelectionSort(int[] arr) throws IOException;
	int[] recursiveSelectionSort(int[] array, int index) throws IOException;
	int getCount();
	long getTime();
}
