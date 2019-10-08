package com.coreJava.duoXianCheng.AlgorithmAnimation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This program animates a sort algorithm.
 * 
 * @version 1.01 2007-05-18
 * @author Cay Horstmann
 */
public class AlgorithmAnimation {
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame frame = new AnimationFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * This frame shows the array as it is sorted, together with buttons to
 * single-step the animation or to run it without interruption.
 */
class AnimationFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 300;

	private static final int DEFAULT_HEIGHT = 300;

	public AnimationFrame() {
		final ArrayComponent comp = new ArrayComponent();
		add(comp, BorderLayout.CENTER);

		final Sorter sorter = new Sorter(comp);

		final JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				sorter.setRun();
			}
		});

		final JButton stepButton = new JButton("Step");
		stepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				sorter.setStep();
			}
		});

		final JPanel buttons = new JPanel();
		buttons.add(runButton);
		buttons.add(stepButton);
		add(buttons, BorderLayout.NORTH);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		final Thread t = new Thread(sorter);
		t.start();
	}
}

/**
 * This component draws an array and marks two elements in the array.
 */
class ArrayComponent extends JComponent {
	private Double marked1;

	private Double marked2;

	private Double[] values;

	@Override
	public synchronized void paintComponent(final Graphics g) // Called on the
																// event
																// dispatch
																// thread
	{
		if (values == null) {
			return;
		}
		final Graphics2D g2 = (Graphics2D) g;
		final int width = getWidth() / values.length;
		for (int i = 0; i < values.length; i++) {
			final double height = values[i] * getHeight();
			final Rectangle2D bar = new Rectangle2D.Double(width * i, 0, width, height);
			if (values[i] == marked1 || values[i] == marked2) {
				g2.fill(bar);
			} else {
				g2.draw(bar);
			}
		}
	}

	/**
	 * Sets the values to be painted. Called on the sorter thread.
	 * 
	 * @param values
	 *            the array of values to display
	 * @param marked1
	 *            the first marked element
	 * @param marked2
	 *            the second marked element
	 */
	public synchronized void setValues(final Double[] values, final Double marked1, final Double marked2) {
		this.values = values.clone();
		this.marked1 = marked1;
		this.marked2 = marked2;
		repaint();
	}
}

/**
 * This runnable executes a sort algorithm. When two elements are compared, the
 * algorithm pauses and updates a component.
 */
class Sorter implements Runnable {
	private static final int DELAY = 100;

	private static final int VALUES_LENGTH = 30;

	private final Double[] values;

	private final ArrayComponent component;

	private final Semaphore gate;
	private volatile boolean run;

	/**
	 * Constructs a Sorter.
	 * 
	 * @param values
	 *            the array to be sorted
	 * @param comp
	 *            the component on which to display the sorting progress
	 */
	public Sorter(final ArrayComponent comp) {
		values = new Double[VALUES_LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = new Double(Math.random());
		}
		this.component = comp;
		this.gate = new Semaphore(1);
		this.run = false;
	}

	@Override
	public void run() {
		final Comparator<Double> comp = new Comparator<Double>() {
			@Override
			public int compare(final Double i1, final Double i2) {
				component.setValues(values, i1, i2);
				try {
					if (run) {
						Thread.sleep(DELAY);
					} else {
						gate.acquire();
					}
				} catch (final InterruptedException exception) {
					Thread.currentThread().interrupt();
				}
				return i1.compareTo(i2);
			}
		};
		Arrays.sort(values, comp);
		component.setValues(values, null, null);
	}

	/**
	 * Sets the sorter to "run" mode. Called on the event dispatch thread.
	 */
	public void setRun() {
		run = true;
		gate.release();
	}

	/**
	 * Sets the sorter to "step" mode. Called on the event dispatch thread.
	 */
	public void setStep() {
		run = false;
		gate.release();
	}
}
