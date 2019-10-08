package com.coreJava.duoXianCheng.Bounce;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The component that draws the balls.
 * 
 * @version 1.33 2007-05-17
 * @author Cay Horstmann
 */
public class BallComponent extends JPanel {
	private final ArrayList<Ball> balls = new ArrayList<Ball>();

	/**
	 * Add a ball to the component.
	 * 
	 * @param b
	 *            the ball to add
	 */
	public void add(final Ball b) {
		balls.add(b);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g); // erase background
		final Graphics2D g2 = (Graphics2D) g;
		for (final Ball b : balls) {
			g2.fill(b.getShape());
		}
	}
}
