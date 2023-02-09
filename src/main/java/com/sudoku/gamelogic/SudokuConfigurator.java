package com.sudoku.gamelogic;

import com.sudoku.problemdomain.Coordinates;
import com.sudoku.problemdomain.Sudoku;
import com.sudoku.utilities.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/** Class that manages the configuration of a Sudoku Game. */
public class SudokuConfigurator {
    public static int[][] getNewGameBoard() {
        return initGame(getSolvedGame());
    }

    // Initialise a solvable game.
    private static int[][] initGame(int[][] solvedBoard) {
        Random random = new Random(System.currentTimeMillis());
        boolean isSolvable = false;
        int[][] solvableBoard = new int[Sudoku.LIMIT][Sudoku.LIMIT];
        while (!isSolvable) {
            Helper.duplicateValues(solvedBoard, solvableBoard);
            // Remove x numbers from solved board.
            removeValsFromBoard(random, solvableBoard, 40);
            // Create solveable starting board for the user.
            int[][] startBoard = new int[Sudoku.LIMIT][Sudoku.LIMIT];
            Helper.duplicateValues(solvableBoard, startBoard);
            isSolvable = SudokuSolver.isSolvable(startBoard);
        }
        return solvableBoard;
    }

    private static void removeValsFromBoard(Random random, int[][] solvableBoard, int n) {
        int i = 0;
        while (i < n) {
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
        int[][] newBoard = new int[Sudoku.LIMIT][Sudoku.LIMIT];
        Random random = new Random(System.currentTimeMillis());
        for (int num = 1; num <= 9; num++) {
            // Keep allocating numbers.
            allocateValues(num, random, newBoard);
        }
        return newBoard;
    }

    private static void allocateValues(int num, Random random, int[][] newBoard) {
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
            int nextX = random.nextInt(Sudoku.LIMIT);
            int nextY = random.nextInt(Sudoku.LIMIT);
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
