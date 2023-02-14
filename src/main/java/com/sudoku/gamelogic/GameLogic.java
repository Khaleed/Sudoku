package com.sudoku.gamelogic;

import com.sudoku.constants.GameState;
import com.sudoku.constants.Rank;
import com.sudoku.model.Sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameLogic {
    public static Sudoku getNewGame() {
        return new Sudoku(GameState.NEW, SudokuConfigurator.getNewGameBoard());
    }

    public static GameState finishedGame(int[][] board) {
        return (!areAllSquaresFilled(board) || !isMoveValid(board)) ?
                GameState.INPLAY : GameState.FINISHED;
    }

    public static boolean areAllSquaresFilled(int[][] board) {
        for (int i = 0; i < Sudoku.LIMIT; i++) {
            for (int j = 0; j < Sudoku.LIMIT; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    public static boolean isMoveValid(int[][] board) {
        return areValsInRowValid(board) && areValsInColValid(board) && areSquaresValid(board);
    }

    private static boolean areSquaresValid(int[][] board) {
        return (isRowOfSquaresValid(Rank.TOP, board) &&
                isRowOfSquaresValid(Rank.MIDDLE, board) &&
                isRowOfSquaresValid(Rank.BOTTOM, board));
    }

    private static boolean isRowOfSquaresValid(Rank rank, int[][] board) {
        switch (rank) {
            case TOP -> {
                if (isSquareValid(0, 0, board)) return true;
                if (isSquareValid(0, 3, board)) return true;
                return isSquareValid(0, 6, board);
            }
            case MIDDLE -> {
                if (isSquareValid(3, 0, board)) return true;
                if (isSquareValid(3, 3, board)) return true;
                return isSquareValid(3, 6, board);
            }
            case BOTTOM -> {
                if (isSquareValid(6, 0, board)) return true;
                if (isSquareValid(6, 3, board)) return true;
                return isSquareValid(6, 6, board);
            }
            default -> {return false;}
        }
    }

    // TODO(Khalid): Re-assess / test.
    private static boolean isSquareValid(int i, int j, int[][] board) {
        List<Integer> squares = new ArrayList<>();
        int xEnd = i + 3;
        int yEnd = j + 3;
        while (i < xEnd) {
            while (j < yEnd) {
                squares.add(board[xEnd][yEnd]);
                i += 1;
                j += 1;
            }
            i -= 3;
        }
        return isDuplicate(squares);
    }

    private static boolean isDuplicate(List<Integer> squares) {
        Set<Integer> set = new HashSet<>();
        for (Integer square : squares) {
            if (set.contains(square)) return true;
            set.add(square);
        }
        return false;
    }

    private static boolean areValsInColValid(int[][] board) {
        for (int i = 0; i < Sudoku.LIMIT; i++) {
            List<Integer> valsInCol = new ArrayList<>();
            for (int j = 0; j < Sudoku.LIMIT; j++) {
                valsInCol.add(board[i][j]);
            }
            if (isDuplicate(valsInCol)) return false;
        }
        return true;
    }

    private static boolean areValsInRowValid(int[][] board) {
        for (int i = 0; i < Sudoku.LIMIT; i++) {
            List<Integer> valsInRow = new ArrayList<>();
            for (int j = 0; j < Sudoku.LIMIT; j++) {
                valsInRow.add(board[i][j]);
            }
            if (isDuplicate(valsInRow)) return false;
        }
        return true;
    }
}
