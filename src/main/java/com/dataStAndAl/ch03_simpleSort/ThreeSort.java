package com.dataStAndAl.ch03_simpleSort;

public class ThreeSort {
	public static void main(final String[] args) {
		int out;
		int in;
		final int nElems = 10;
		int tmp;
		final int[] a = new int[10];

		// 冒泡排序，从右边开始
		for (out = nElems - 1; out > 1; out--) {
			for (in = 0; in < in; in++) {
				if (a[in] > a[in + 1]) {
					swap(in, in + 1);
				}
			}
		}

		// 选择排序，从左边开始
		for (out = 0; out < nElems - 1; out++) {
			// 这里要减1，防止越界
			tmp = out;// 最小下标
			for (in = out + 1; in < nElems; in++) {
				if (a[in] < a[out]) {
					tmp = in;
				}
			}
			swap(out, tmp);
		}

		// 插入排序，从左边开始
		// 从1开始是因为下面减了1
		for (out = 1; out < nElems; out++) {
			tmp = a[out];
			in = out;
			while (in > 0 && a[in - 1] >= tmp) {
				a[in] = a[in - 1];
				in--;
			}
			a[in] = tmp;
		}

	}

	public static void swap(final int a, final int b) {
		//
	}

}
