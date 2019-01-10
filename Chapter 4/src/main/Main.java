package main;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		//Will pop up a window asking for the input. Second parameter is the default text in the box
		String input = JOptionPane.showInputDialog("Enter radius", "0");
		
		//Try to get a number out of the string
		if (input == null) {
			return;
		}
		
		double radius = Double.parseDouble(input);
			
		if (radius < 0) {
			JOptionPane.showMessageDialog(null, "Error: Must be greater than 0");
		} else {
			double area = Math.PI * Math.pow(radius, 2);
			JOptionPane.showMessageDialog(null, "The area is " + area);
		}
		
	}

}
