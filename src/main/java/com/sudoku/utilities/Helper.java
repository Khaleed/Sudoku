package com.sudoku.utilities;

import com.sudoku.problemdomain.Sudoku;

import java.util.Arrays;

public class Helper {
    public static int[][] copyToNewArray(int[][] arr) {
        return Arrays.copyOf(arr, Sudoku.LIMIT);
    }

    public static void duplicateValues(int[][] xs, int[][] ys) {
        for (int i = 0; i < Sudoku.LIMIT; i++) {
            System.arraycopy(xs[i], 0, ys[i], 0, xs[i].length);
        }
    }
}
