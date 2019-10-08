package com.coreJava.duoXianCheng.FutureTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @version 1.0 2004-08-01
 * @author Cay Horstmann
 */
public class FutureTest {
	public static void main(final String[] args) {
		final Scanner in = new Scanner(System.in);
		System.out.print("Enter base directory (e.g. /usr/local/jdk5.0/src): ");
		final String directory = in.nextLine();
		System.out.print("Enter keyword (e.g. volatile): ");
		final String keyword = in.nextLine();

		final MatchCounter counter = new MatchCounter(new File(directory), keyword);
		final FutureTask<Integer> task = new FutureTask<Integer>(counter);
		final Thread t = new Thread(task);
		t.start();
		try {
			System.out.println(task.get() + " matching files.");
		} catch (final ExecutionException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
		}
	}
}

/**
 * This task counts the files in a directory and its subdirectories that contain
 * a given keyword.
 */
class MatchCounter implements Callable<Integer> {
	private final File directory;

	private final String keyword;

	private int count;

	/**
	 * Constructs a MatchCounter.
	 * 
	 * @param directory
	 *            the directory in which to start the search
	 * @param keyword
	 *            the keyword to look for
	 */
	public MatchCounter(final File directory, final String keyword) {
		this.directory = directory;
		this.keyword = keyword;
	}

	@Override
	public Integer call() {
		count = 0;
		try {
			final File[] files = directory.listFiles();
			final ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();

			for (final File file : files) {
				if (file.isDirectory()) {
					final MatchCounter counter = new MatchCounter(file, keyword);
					final FutureTask<Integer> task = new FutureTask<Integer>(counter);
					results.add(task);
					final Thread t = new Thread(task);
					t.start();
				} else {
					if (search(file)) {
						count++;
					}
				}
			}

			for (final Future<Integer> result : results) {
				try {
					count += result.get();
				} catch (final ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (final InterruptedException e) {
		}
		return count;
	}

	/**
	 * Searches a file for a given keyword.
	 * 
	 * @param file
	 *            the file to search
	 * @return true if the keyword is contained in the file
	 */
	public boolean search(final File file) {
		try {
			final Scanner in = new Scanner(new FileInputStream(file));
			boolean found = false;
			while (!found && in.hasNextLine()) {
				final String line = in.nextLine();
				if (line.contains(keyword)) {
					found = true;
				}
			}
			in.close();
			return found;
		} catch (final IOException e) {
			return false;
		}
	}
}
