package com.sudoku.gamelogic;

import com.sudoku.model.Coordinates;
import com.sudoku.model.Sudoku;
import com.sudoku.utilities.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.sudoku.gamelogic.GameLogic.*;

/** Class that manages the configuration of a Sudoku Game. */
public class SudokuConfigurator {
    public static int[][] getNewGameBoard() {
        return initGame(getSolvedGame());
    }

    // Initialise a solvable game.
    private static int[][] initGame(int[][] board) {
        boolean isSolvable = false;
        int[][] solvableBoard = new int[Sudoku.LIMIT][Sudoku.LIMIT];
        while (!isSolvable) {
            Helper.duplicateValues(board, solvableBoard);
            // Remove x numbers from solved board.
            removeValsFromBoard(solvableBoard);
            // Create solveable starting board for the user.
            int[][] startBoard = new int[Sudoku.LIMIT][Sudoku.LIMIT];
            Helper.duplicateValues(solvableBoard, startBoard);
            isSolvable = SudokuSolver.isSolvable(startBoard);
        }
        return solvableBoard;
    }

    private static void removeValsFromBoard(int[][] solvableBoard) {
        Random random = new Random(System.currentTimeMillis());
        int i = 0;
        while (i < 40) {
            int nextX = random.nextInt(Sudoku.LIMIT);
            int nextY = random.nextInt(Sudoku.LIMIT);
            if (!isSquareEmpty(solvableBoard, nextX, nextY)) {
                solvableBoard[nextX][nextY] = 0;
                i += 1;
            }
        }
    }

    // TODO(Khalid): Implement a better algorithm than the brute force version.
    private static int[][] getSolvedGame() {
        int[][] board = new int[Sudoku.LIMIT][Sudoku.LIMIT];
        solve(board);
        return board;
    }

    /**
     * Backtracking algorithm.
     */
    private static void solve(int[][] board) {
        for (int row = 0; row < Sudoku.LIMIT; row++) {
            for (int col = 0; col < Sudoku.LIMIT; col++) {
                if (isSquareEmpty(board, row, col)) {
                    for (int k = Sudoku.START_VALUE; k <= Sudoku.MAX_VALUE; k++) {
                        board[row][col] = k;
                        if (isMoveValid(board, row, col)) {
                            solve(board);
                        }
                        board[row][col] = 0;
                    }
                }
            }
        }
    }

    private static boolean isSquareEmpty(int[][] board, int row, int col) {
        return board[row][col] == 0;
    }

    private static void clear(int[][] board) {
        Arrays.stream(board).forEach(xs -> Arrays.fill(xs, 0));
    }

    private static void clear(int[][] board, List<Coordinates> coordinates) {
        coordinates.forEach(c -> {board[c.getX()][c.getY()] = 0;});
        coordinates.clear();
    }
}
