package com.sudoku.gamelogic;

import com.sudoku.model.Coordinates;
import com.sudoku.model.Sudoku;

/** Sudoku Solver Class with Recursive Backtracker naive algorithm. */
public class SudokuSolver {
    // TODO(Khalid): Inject Solver -> Dependency injection and remove static methods.
    // TODO(Khalid): Wre-Write and test logic of thi method.
    // Algorithm based on: https://pi.math.cornell.edu/~mec/Summer2009/meerkamp/Site/Solving_any_Sudoku_I.html
    public static boolean isSolvable(int[][] board) {
        Coordinates[] emptyCells = enumerateInTypeWriterOrder(board);
        int i = 0;
        int num = 1;
        // For the 40 empty cells.
        // i = 0
        while (i < 40) {
            // Take the first cell.
            // Example = [[0, 2], [2, 3], [8, 2] ... ]
            Coordinates currCell = emptyCells[i];
            // Try placing 1 - 9.
            while (num < 10 || !(GameLogic.isMoveValid(board))) {
                // Board = [[]...[1], [2], ...]
                board[currCell.getX()][currCell.getY()] = num;
                num += 1;
                // Valid?
                if (!GameLogic.isMoveValid(board)) {
                    num += 1;
                    board[currCell.getX()][currCell.getY()] = num;
                } else if (num == 9 && (!GameLogic.isMoveValid(board))) {
                    if (i == 0) {
                        return false;
                    } else {
                        // Remove 9 and assign curr to prev empty cell.
                        board[currCell.getX()][currCell.getY()] = 0;
                        i -= 1;
                    }
                } else {
                    i += 1;
                    if (i == 39) return true;
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
                    if (iter == 39) return coordinates;
                    iter += 1;
                }
            }
        }
        return coordinates;
    }
}
