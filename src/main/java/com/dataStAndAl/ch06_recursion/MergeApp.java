package com.dataStAndAl.ch06_recursion;

// merge.java
// demonstrates merging two arrays into a third
// to run this program: C>java MergeApp
////////////////////////////////////////////////////////////////
class MergeApp {
	// -----------------------------------------------------------
	// display array
	public static void display(final int[] theArray, final int size) {
		for (int j = 0; j < size; j++) {
			System.out.print(theArray[j] + " ");
		}
		System.out.println("");
	}

	// -----------------------------------------------------------
	public static void main(final String[] args) {
		final int[] arrayA = { 23, 47, 81, 95 };
		final int[] arrayB = { 7, 14, 39, 55, 62, 74 };
		final int[] arrayC = new int[10];

		merge(arrayA, 4, arrayB, 6, arrayC);
		display(arrayC, 10);
	} // end main()
	// -----------------------------------------------------------
	// merge A and B into C

	public static void merge(final int[] arrayA, final int sizeA, final int[] arrayB, final int sizeB,
			final int[] arrayC) {
		int aDex = 0, bDex = 0, cDex = 0;

		while (aDex < sizeA && bDex < sizeB) {
			if (arrayA[aDex] < arrayB[bDex]) {
				arrayC[cDex++] = arrayA[aDex++];
			} else {
				arrayC[cDex++] = arrayB[bDex++];
			}
		}

		while (aDex < sizeA) {
			arrayC[cDex++] = arrayA[aDex++]; // but arrayA isn't
		}

		while (bDex < sizeB) {
			arrayC[cDex++] = arrayB[bDex++]; // but arrayB isn't
		}
	} // end merge()
} // end class MergeApp
////////////////////////////////////////////////////////////////
