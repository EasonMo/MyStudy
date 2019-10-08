package com.coreJava.duoXianCheng.Bounce;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Shows an animated bouncing ball.
 * 
 * @version 1.33 2007-05-17
 * @author Cay Horstmann
 */
public class Bounce {
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame frame = new BounceFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * The frame with ball component and buttons.
 */
class BounceFrame extends JFrame {
	public static final int DEFAULT_WIDTH = 450;

	public static final int DEFAULT_HEIGHT = 350;

	public static final int STEPS = 1000;

	public static final int DELAY = 3;
	private final BallComponent comp;

	/**
	 * Constructs the frame with the component for showing the bouncing ball and
	 * Start and Close buttons
	 */
	public BounceFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Bounce");

		comp = new BallComponent();
		add(comp, BorderLayout.CENTER);
		final JPanel buttonPanel = new JPanel();
		addButton(buttonPanel, "Start", new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				addBall();
			}
		});

		addButton(buttonPanel, "Close", new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				System.exit(0);
			}
		});
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Adds a bouncing ball to the panel and makes it bounce 1,000 times.
	 */
	public void addBall() {
		try {
			final Ball ball = new Ball();
			comp.add(ball);

			for (int i = 1; i <= STEPS; i++) {
				ball.move(comp.getBounds());
				comp.paint(comp.getGraphics());
				Thread.sleep(DELAY);
			}
		} catch (final InterruptedException e) {
		}
	}

	/**
	 * Adds a button to a container.
	 * 
	 * @param c
	 *            the container
	 * @param title
	 *            the button title
	 * @param listener
	 *            the action listener for the button
	 */
	public void addButton(final Container c, final String title, final ActionListener listener) {
		final JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}
}