/* Author: Jason Morgan
 * Date: February 4, 2017
 * Purpose: This program is designed to run the 
 * Selection Sort algorithm.
 */

package benchmarkSort;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BenchmarkSorts extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final int numOfRuns = 50;
    private int[] dataSize;
    private long[][][] times;
    private int[][][] counts;
    private SelectionSort mySort;
    private final ExecutorService executor;
    private JTable jTable;
    
    BenchmarkSorts(int[] sizes) {
    	dataSize = sizes;
    	times = new long[sizes.length][2][numOfRuns];
        counts = new int[sizes.length][2][numOfRuns];
        executor = Executors.newFixedThreadPool(numOfRuns);
        try {
			runSorts();
		} catch (UnsortedException e) {
			e.printStackTrace();
		}
    }

    public void runSorts() throws UnsortedException{
        for(int i = 0; i < dataSize.length; i++) {//for each data set
            for(int j = 0; j < numOfRuns; j++) {//for size of data set
            	int[] data = randomNumberGenerator(dataSize[i]);
            	Future<?> sortFuture = executor.submit(new RunSorts(data, i, j));
            	try {
					sortFuture.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
            }
        }
        displayReports();
    }//end runSorts
    
    class RunSorts implements Runnable {
    	int[] dataRun;
    	int i, j;
    	private RunSorts(int[] test, int i, int j) {
    		this.dataRun = test;
    		this.i = i;
    		this.j = j;
    	}
		@Override
		public void run() {			
			mySort = new SelectionSort();
			//copy array data
			int[] rDat = new int[dataSize[i] + 1];
            int[] iDat = new int[dataSize[i]];
            System.arraycopy(dataRun, 0, rDat, 1, dataRun.length);
			System.arraycopy(dataRun, 0, iDat, 0, dataRun.length);
			//begin iterative sort
			try {
				mySort.iterativeSelectionSort(iDat);
			} catch (IOException e) {
				e.printStackTrace();
			}
			counts[i][0][j] = mySort.getCount();
			times[i][0][j] = mySort.getTime();
			//begin recursive sort
			long start = System.nanoTime();
			try {
				mySort.displaySorts(rDat);
				mySort.recursiveSelectionSort(rDat, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			counts[i][1][j] = mySort.getCount();
			times[i][1][j] = System.nanoTime() - start;
		}
    }//end RunSorts 

    public void displayReports() {
        double[] iterativeData;
        double[] recursiveData;
        Object[][] rows = new Object[dataSize.length][9];

        for(int i = 0; i < dataSize.length; i++) {
        	double[] arrThree = new double[9];
        	iterativeData = getFigures(times[i][0], counts[i][0]);
            recursiveData = getFigures(times[i][1], counts[i][1]);
            System.arraycopy(iterativeData, 0, arrThree, 0, iterativeData.length); 
            System.arraycopy(recursiveData, 0, arrThree, iterativeData.length, recursiveData.length);
            rows[i][0] = dataSize[i];
            for(int j = 1; j < 9; j++)
            	rows[i][j] = arrThree[j-1];
        }             
		Object[] colomnNames = {"n", "Iterative - Avg Critical Opp Cnt", "Iterative - Stnd Deviation of Cnt", "Iterative - Avg Execution Time", "Iterative - Stnd Deviation of Time", 
				"Recursive - Avg Critical Opp Cnt", "Recursive - Stnd Deviation of Cnt", "Recursive - Avg Execution Time", "Recursive - Stnd Deviation of Time"};
		
		JFrame f = new JFrame("Statistical Results");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
	    jTable = new JTable(rows, colomnNames);
	    
	    JScrollPane scrollPane = new JScrollPane(jTable);
	    	    
	    f.add(scrollPane);
	    f.setSize(1275, 250);
	    f.setVisible(true);
    }//end display reports

    private static int[] randomNumberGenerator(int size) {
    	Random random = new Random();
        int[] numbers = new int[size];
        for(int i = 0; i < size; i++)
            numbers[i] = random.nextInt(20000) + 1;
        return numbers;
    }//end randomNumberGenerator

    private double[] getFigures(long[] times, int[] counts) {
    	double totalTimes = 0;
    	double totalCounts = 0;
    	double meanTime = 0;
        double meanCount = 0;
        double timeVariance = 0;
        double countVariance = 0;
        double timeDeviation = 0;
        double countDeviation = 0;

        for(int i = 0; i < counts.length; i++) {
        	totalTimes += times[i] / 1000000000.0;
        	totalCounts += counts[i];
        }
        //calculate averages
        meanTime = totalTimes / times.length;
        meanCount = totalCounts / counts.length;
        //sum all values from (X1-u)^2 to (Xn-u)^2
        double timeSums = 0;
		double countSums = 0;
		for(int i = 0; i < counts.length; i++) {
            timeSums += ((times[i]/1000000000.0) - meanTime) * ((times[i]/1000000000.0) - meanTime);
            countSums += (counts[i] - meanCount) * (counts[i] - meanCount);
        }
        //find the mean of squared sums
        timeVariance = timeSums / times.length;
        countVariance = countSums / counts.length;
        //take square root
        timeDeviation = Math.sqrt(timeVariance);
        countDeviation = Math.sqrt(countVariance);

        double[] figures = {meanCount, countDeviation, meanTime, timeDeviation};
        return figures;
    }//end getFigures
}