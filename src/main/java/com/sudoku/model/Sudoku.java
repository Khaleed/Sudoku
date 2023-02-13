package com.sudoku.model;

import com.sudoku.constants.GameState;
import com.sudoku.utilities.Helper;

import java.io.Serializable;

/** Class that represents a Sudoku game. */
public class Sudoku implements Serializable {
    public static final int LIMIT = 9;
    private final GameState gameState;
    private final int[][] board;

    public Sudoku(GameState gameState, int[][] grid) {
        this.gameState = gameState;
        this.board = grid;
    }

    public int[][] getGridCopy() {
        return Helper.copyToNewArray(board);
    }

    public GameState getGameState() {
        return gameState;
    }
}