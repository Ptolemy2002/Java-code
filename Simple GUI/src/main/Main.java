package main;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame gui = new JFrame();
		gui.setTitle("Test window");
		gui.setSize(300, 200);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = gui.getContentPane();
		
		TextPanel panel = new TextPanel(Color.YELLOW, Color.BLACK, "Test suceeded!");
		pane.add(panel);
		
		gui.setVisible(true);
	}

}
