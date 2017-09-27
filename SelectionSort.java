/* Author: Jason Morgan
 * Date: February 4, 2017
 * Purpose: This program is designed to run the 
 * Selection Sort algorithm.
 */

package benchmarkSort;

import java.io.IOException;

public class SelectionSort implements SortInterface {
	private int count;
    private long time;
    
    public SelectionSort() {    	
    	count = 0;
        time = 0;
    }
	
	public int[] iterativeSelectionSort(int[] arr) throws IOException {
        count = 0;//counts calls to iterativeSelectionSort
		displaySorts(arr);
        long start = System.nanoTime();
        
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
            	count += 1;
            	if (arr[j] < arr[index])
                    index = j;
            }
            int smallerNumber = arr[index]; 
            arr[index] = arr[i];
            arr[i] = smallerNumber;
            displaySorts(arr);
        }
        time = System.nanoTime() - start;
        return arr;
    }//end iterativeSelectionSort
	
	public int[] recursiveSelectionSort(int[] array, int index) throws IOException {
        count = index + 1;//counts calls to recursiveSelectionSort
	    if ( index >= array.length - 1 )
	        return array;
	    int minIndex = index;
	    for ( int i = index + 1; i < array.length; i++ ) {
	        if (array[i] < array[minIndex] )
	            minIndex = i;
	    }
	    int temp = array[index];
	    array[index] = array[minIndex];
	    array[minIndex] = temp;
	    displaySorts(array);
	    return recursiveSelectionSort(array, index + 1);
	}//end recursiveSelectionSort
	
    public int getCount() { 
    	return count; 
    	}

    public long getTime() { 
    	return time; 
    	}
    	   
    public void displaySorts(int[] arry) throws IOException {
    	if (arry.length > 100)
    		return;
    		for (int k: arry) {
    			System.out.print("- " + k + " -");
    		}
    		System.out.print("\n");
    }//end displaySorts
    
    public static void itrCheckArray(int[] a) throws UnsortedException {
        for (int i = 0; i < a.length - 1; i++)
            if(a[i] > a[i + 1]) {
                for(int j = 0; j < a.length -1; j++)
                    System.out.print(" "  + a[j]);
                throw new UnsortedException("The array was not sorted " +
                        "correctly: \n" +
                        a[i] + " > " + a[i+1] + " at indices " + i +
                        " and " + (i+1) + " respectively.\n");
            }
    }

    public static void recCheckArray(int[] a) throws UnsortedException {
        for (int i = 1; i < a.length - 1; i++)
            if(a[i] > a[i + 1]) {
                for(int j = 0; j < a.length -1; j++)
                    System.out.print(" "  + a[j]);
                throw new UnsortedException("The array was not sorted " +
                        "correctly: \n" +
                        a[i] + " > " + a[i+1] + " at indices " + i +
                        " and " + (i+1) + " respectively.\n");
            }
    }

}
