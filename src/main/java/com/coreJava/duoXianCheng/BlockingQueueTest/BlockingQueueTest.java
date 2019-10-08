package com.coreJava.duoXianCheng.BlockingQueueTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @version 1.0 2004-08-01
 * @author Cay Horstmann
 */
public class BlockingQueueTest {
	public static void main(final String[] args) {
		final Scanner in = new Scanner(System.in);
		System.out.print("Enter base directory (e.g. /usr/local/jdk1.6.0/src): ");
		final String directory = in.nextLine();
		System.out.print("Enter keyword (e.g. volatile): ");
		final String keyword = in.nextLine();

		final int FILE_QUEUE_SIZE = 10;
		final int SEARCH_THREADS = 100;

		final BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

		final FileEnumerationTask enumerator = new FileEnumerationTask(queue, new File(directory));
		new Thread(enumerator).start();
		for (int i = 1; i <= SEARCH_THREADS; i++) {
			new Thread(new SearchTask(queue, keyword)).start();
		}
	}
}

/**
 * This task enumerates all files in a directory and its subdirectories.
 */
class FileEnumerationTask implements Runnable {
	public static File DUMMY = new File("");

	private final BlockingQueue<File> queue;

	private final File startingDirectory;

	/**
	 * Constructs a FileEnumerationTask.
	 * 
	 * @param queue
	 *            the blocking queue to which the enumerated files are added
	 * @param startingDirectory
	 *            the directory in which to start the enumeration
	 */
	public FileEnumerationTask(final BlockingQueue<File> queue, final File startingDirectory) {
		this.queue = queue;
		this.startingDirectory = startingDirectory;
	}

	/**
	 * Recursively enumerates all files in a given directory and its
	 * subdirectories
	 * 
	 * @param directory
	 *            the directory in which to start
	 */
	public void enumerate(final File directory) throws InterruptedException {
		final File[] files = directory.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				enumerate(file);
			} else {
				queue.put(file);
			}
		}
	}

	@Override
	public void run() {
		try {
			enumerate(startingDirectory);
			queue.put(DUMMY);
		} catch (final InterruptedException e) {
		}
	}
}

/**
 * This task searches files for a given keyword.
 */
class SearchTask implements Runnable {
	private final BlockingQueue<File> queue;

	private final String keyword;

	/**
	 * Constructs a SearchTask.
	 * 
	 * @param queue
	 *            the queue from which to take files
	 * @param keyword
	 *            the keyword to look for
	 */
	public SearchTask(final BlockingQueue<File> queue, final String keyword) {
		this.queue = queue;
		this.keyword = keyword;
	}

	@Override
	public void run() {
		try {
			boolean done = false;
			while (!done) {
				final File file = queue.take();
				if (file == FileEnumerationTask.DUMMY) {
					queue.put(file); // 在这里控制所有搜索线程退出
					done = true;
				} else {
					search(file);
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
		}
	}

	/**
	 * Searches a file for a given keyword and prints all matching lines.
	 * 
	 * @param file
	 *            the file to search
	 */
	public void search(final File file) throws IOException {
		final Scanner in = new Scanner(new FileInputStream(file));
		int lineNumber = 0;
		while (in.hasNextLine()) {
			lineNumber++;
			final String line = in.nextLine();
			if (line.contains(keyword)) {
				System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
			}
		}
		in.close();
	}
}
