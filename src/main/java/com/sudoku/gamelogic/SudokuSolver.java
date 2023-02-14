package com.sudoku.gamelogic;

import com.sudoku.model.Coordinates;
import com.sudoku.model.Sudoku;

/** Sudoku Solver Class with Recursive Backtracker naive algorithm. */
public class SudokuSolver {
    // TODO(Khalid): Inject Solver -> Dependency injection and remove static methods.
    // Algorithm based on: https://pi.math.cornell.edu/~mec/Summer2009/meerkamp/Site/Solving_any_Sudoku_I.html
    public static boolean isSolvable(int[][] board) {
        Coordinates[] emptyCoordinates = enumerateInTypeWriterOrder(board);
        int i = 0;
        int num;
        while (i < 40) {
            Coordinates currCell = emptyCoordinates[i];
            num = 1;
            while (num < 10) {
                board[currCell.getX()][currCell.getY()] = num;
                if (!GameLogic.isMoveValid(board)) {
                    num += 1;
                } else {
                    i += 1;
                    if (i == 39) {
                        return true;
                    }
                }
                if (num == 9 && !(GameLogic.isMoveValid(board))) {
                    if (i == 0) {
                        return false;
                    } else {
                        board[currCell.getX()][currCell.getY()] = 0;
                        i -= 1;
                    }
                }
            }
        }
        return false;
    }

    /** Method that enumerates empty squares in board as single n-sized array in left, right, top, bottom order. */
    private static Coordinates[] enumerateInTypeWriterOrder(int[][] board) {
        Coordinates[] coordinates = new Coordinates[40];
        int iter = 0;
        for (int i = 0; i < Sudoku.LIMIT; i++) {
            for (int j = 0; j < Sudoku.LIMIT; j++) {
                if (board[i][j] == 0) {
                    coordinates[iter] = new Coordinates(i, j);
                    iter += 1;
                    if (iter == 39) return coordinates;
                }
            }
        }
        return coordinates;
    }
}
