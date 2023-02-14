package com.sudoku.gamelogic;

import com.sudoku.model.Coordinates;
import com.sudoku.model.Sudoku;
import com.sudoku.utilities.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/** Class that manages the configuration of a Sudoku Game. */
public class SudokuConfigurator {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

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
        int i = 0;
        while (i < 40) {
            int nextX = RANDOM.nextInt(Sudoku.LIMIT);
            int nextY = RANDOM.nextInt(Sudoku.LIMIT);
            if (!isSquareEmpty(solvableBoard, nextX, nextY)) {
                solvableBoard[nextX][nextY] = 0;
                i += 1;
            }
        }
    }

    // TODO(Khalid): Implement a better algorithm than the brute force version.
    private static int[][] getSolvedGame() {
        int[][] newBoard = new int[Sudoku.LIMIT][Sudoku.LIMIT];
        for (int num = 1; num <= Sudoku.LIMIT; num++) {
            // Keep allocating numbers.
            allocateValues(num, newBoard);
        }
        return newBoard;
    }

    /** TODO(Khalid): Refactor to include graph coloring algorithm. */
    private static void allocateValues(int num, int[][] newBoard) {
        int numAlloc = 0;
        List<Coordinates> coordinates = new ArrayList<>();
        int interrupt = 0;
        int attempts = 0;
        while (numAlloc < Sudoku.LIMIT) {
            // Pragmatic -> fail fast!
            if (interrupt > 200) {
                clear(newBoard, coordinates);
                interrupt = 0;
                numAlloc = 0;
                attempts += 1;
                if (attempts > 500) {
                    clear(newBoard);
                    attempts = 0;
                    num = 1;
                }
            }
            int nextX = RANDOM.nextInt(Sudoku.LIMIT);
            int nextY = RANDOM.nextInt(Sudoku.LIMIT);
            if (isSquareEmpty(newBoard, nextX, nextY)) {
                newBoard[nextX][nextY] = num;
                if (GameLogic.isMoveValid(newBoard)) {
                    coordinates.add(new Coordinates(nextX, nextY));
                    numAlloc += 1;
                } else {
                    newBoard[nextX][nextY] = 0;
                    interrupt += 1;
                }
            }
        }
    }

    private static boolean isSquareEmpty(int[][] board, int x, int y) {
        return board[x][y] == 0;
    }

    private static void clear(int[][] board) {
        Arrays.stream(board).forEach(xs -> Arrays.fill(xs, 0));
    }

    private static void clear(int[][] board, List<Coordinates> coordinates) {
        coordinates.forEach(c -> {board[c.getX()][c.getY()] = 0;});
        coordinates.clear();
    }
}
