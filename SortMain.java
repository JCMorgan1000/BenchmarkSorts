/* Author: Jason Morgan
 * Date: February 4, 2017
 * Purpose: This program is designed to run the 
 * Selection Sort algorithm.
 */

package benchmarkSort;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SortMain extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L; 
	private JButton start = new JButton("Start"); JButton browseButton = new JButton("Browse...");
	private JTextField directoryTextField = new JTextField(25); JLabel directoryLabel = new JLabel("Input File:");
	private int[] testSizes = new int[10];
	public SortMain() {
		super("Project 1");
		setSize(450, 175);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		add(directoryLabel);
		add(directoryTextField);
		add(browseButton);
		add(start);
		start.addActionListener(this);
		browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new BenchmarkSorts(testSizes);
	}
	
	private void browseButtonActionPerformed(ActionEvent evt) {                                             
        JFileChooser chooser = new JFileChooser("");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
            System.out.println("Selected File: " + chooser.getSelectedFile() + "\n");
            directoryTextField.setText(String.valueOf(chooser.getSelectedFile()));
            Scanner file = null;
			try {
				file = new Scanner (chooser.getSelectedFile());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
            for (int i=0; i<testSizes.length; i++)
            	testSizes[i] = file.nextInt();
        }        
	}//end browseButtonActionPerformed   
	
	public static void main (String[] args) {
		SortMain window = new SortMain();
		window.setVisible(true);
	}
}
