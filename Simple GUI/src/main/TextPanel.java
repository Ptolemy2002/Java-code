package main;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TextPanel extends JPanel {
	
	public Color backgroundColor;
	public Color textColor;
	public String text;
	
	public TextPanel() {
		super();
		
		this.backgroundColor = Color.BLACK;
		setBackground(backgroundColor);
		this.textColor = Color.WHITE;
		this.text = "undefined";
	}
	
	public TextPanel(Color backColor, Color textColor, String text) {
		super();
		
		this.backgroundColor = backColor;
		setBackground(backgroundColor);
		this.textColor = textColor;
		this.text = text;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(this.textColor);
		g.drawString(this.text, 100, 50);
	}
	
}
