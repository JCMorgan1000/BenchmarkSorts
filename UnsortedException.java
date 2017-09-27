/* Author: Jason Morgan
 * Date: February 4, 2017
 * Purpose: This program is designed to run the 
 * Selection Sort algorithm.
 */

package benchmarkSort;

public class UnsortedException extends Exception {
	private static final long serialVersionUID = 1L;
	public UnsortedException() { super(); }
    public UnsortedException(String s) { super(s); }
}