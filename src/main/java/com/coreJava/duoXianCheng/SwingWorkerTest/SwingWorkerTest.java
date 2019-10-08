package com.coreJava.duoXianCheng.SwingWorkerTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * This program demonstrates a worker thread that runs a potentially
 * time-consuming task.
 * 
 * @version 1.1 2007-05-18
 * @author Cay Horstmann
 */
public class SwingWorkerTest {
	public static void main(final String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame frame = new SwingWorkerFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * This frame has a text area to show the contents of a text file, a menu to
 * open a file and cancel the opening process, and a status line to show the
 * file loading progress.
 */
class SwingWorkerFrame extends JFrame {
	private class ProgressData {
		public int number;
		public String line;
	}

	private class TextReader extends SwingWorker<StringBuilder, ProgressData> {
		private final File file;

		// the following method executes in the worker thread; it doesn't touch
		// Swing components

		private final StringBuilder text = new StringBuilder();

		// the following methods execute in the event dispatch thread

		public TextReader(final File file) {
			this.file = file;
		}

		@Override
		public StringBuilder doInBackground() throws IOException, InterruptedException {
			int lineNumber = 0;
			final Scanner in = new Scanner(new FileInputStream(file));
			while (in.hasNextLine()) {
				final String line = in.nextLine();
				lineNumber++;
				text.append(line);
				text.append("\n");
				final ProgressData data = new ProgressData();
				data.number = lineNumber;
				data.line = line;
				publish(data);
				Thread.sleep(1); // to test cancellation; no need to do this in
									// your programs
			}
			return text;
		}

		@Override
		public void done() {
			try {
				final StringBuilder result = get();
				textArea.setText(result.toString());
				statusLine.setText("Done");
			} catch (final InterruptedException ex) {
			} catch (final CancellationException ex) {
				textArea.setText("");
				statusLine.setText("Cancelled");
			} catch (final ExecutionException ex) {
				statusLine.setText("" + ex.getCause());
			}

			cancelItem.setEnabled(false);
			openItem.setEnabled(true);
		}

		@Override
		public void process(final List<ProgressData> data) {
			if (isCancelled()) {
				return;
			}
			final StringBuilder b = new StringBuilder();
			statusLine.setText("" + data.get(data.size() - 1).number);
			for (final ProgressData d : data) {
				b.append(d.line);
				b.append("\n");
			}
			textArea.append(b.toString());
		}
	}

	public static final int DEFAULT_WIDTH = 450;;

	public static final int DEFAULT_HEIGHT = 350;
	private final JFileChooser chooser;
	private final JTextArea textArea;
	private final JLabel statusLine;
	private final JMenuItem openItem;
	private final JMenuItem cancelItem;

	private SwingWorker<StringBuilder, ProgressData> textReader;

	public SwingWorkerFrame() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		textArea = new JTextArea();
		add(new JScrollPane(textArea));
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		statusLine = new JLabel(" ");
		add(statusLine, BorderLayout.SOUTH);

		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu menu = new JMenu("File");
		menuBar.add(menu);

		openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				// show file chooser dialog
				final int result = chooser.showOpenDialog(null);

				// if file selected, set it as icon of the label
				if (result == JFileChooser.APPROVE_OPTION) {
					textArea.setText("");
					openItem.setEnabled(false);
					textReader = new TextReader(chooser.getSelectedFile());
					textReader.execute();
					cancelItem.setEnabled(true);
				}
			}
		});

		cancelItem = new JMenuItem("Cancel");
		menu.add(cancelItem);
		cancelItem.setEnabled(false);
		cancelItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				textReader.cancel(true);
			}
		});
	}
}