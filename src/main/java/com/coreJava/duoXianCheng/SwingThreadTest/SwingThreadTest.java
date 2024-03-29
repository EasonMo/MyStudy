package com.coreJava.duoXianCheng.SwingThreadTest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This program demonstrates that a thread that runs in parallel with the event
 * dispatch thread can cause errors in Swing components.
 * 
 * @version 1.23 2007-05-17
 * @author Cay Horstmann
 */
public class SwingThreadTest {
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final SwingThreadFrame frame = new SwingThreadFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * This runnable modifies a combo box by randomly adding and removing numbers.
 * This can result in errors because the combo box methods are not synchronized
 * and both the worker thread and the event dispatch thread access the combo
 * box.
 */
class BadWorkerRunnable implements Runnable {
	private final JComboBox combo;

	private final Random generator;

	public BadWorkerRunnable(final JComboBox aCombo) {
		combo = aCombo;
		generator = new Random();
	}

	@Override
	public void run() {
		try {
			while (true) {
				final int i = Math.abs(generator.nextInt());
				if (i % 2 == 0) {
					combo.insertItemAt(i, 0);
				} else if (combo.getItemCount() > 0) {
					combo.removeItemAt(i % combo.getItemCount());
				}
				Thread.sleep(1);
			}
		} catch (final InterruptedException e) {
		}
	}
}

/**
 * This runnable modifies a combo box by randomly adding and removing numbers.
 * In order to ensure that the combo box is not corrupted, the editing
 * operations are forwarded to the event dispatch thread.
 */
class GoodWorkerRunnable implements Runnable {
	private final JComboBox combo;

	private final Random generator;

	public GoodWorkerRunnable(final JComboBox aCombo) {
		combo = aCombo;
		generator = new Random();
	}

	@Override
	public void run() {
		try {
			while (true) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						final int i = Math.abs(generator.nextInt());
						if (i % 2 == 0) {
							combo.insertItemAt(i, 0);
						} else if (combo.getItemCount() > 0) {
							combo.removeItemAt(i % combo.getItemCount());
						}
					}
				});
				Thread.sleep(1);
			}
		} catch (final InterruptedException e) {
		}
	}
}

/**
 * This frame has two buttons to fill a combo box from a separate thread. The
 * "Good" button uses the event queue, the "Bad" button modifies the combo box
 * directly.
 */
class SwingThreadFrame extends JFrame {
	public SwingThreadFrame() {
		setTitle("SwingThreadTest");

		final JComboBox combo = new JComboBox();
		combo.insertItemAt(Integer.MAX_VALUE, 0);
		combo.setPrototypeDisplayValue(combo.getItemAt(0));
		combo.setSelectedIndex(0);

		final JPanel panel = new JPanel();

		final JButton goodButton = new JButton("Good");
		goodButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				new Thread(new GoodWorkerRunnable(combo)).start();
			}
		});
		panel.add(goodButton);
		final JButton badButton = new JButton("Bad");
		badButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				new Thread(new BadWorkerRunnable(combo)).start();
			}
		});
		panel.add(badButton);

		panel.add(combo);
		add(panel);
		pack();
	}
}