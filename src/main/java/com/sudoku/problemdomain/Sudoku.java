package com.sudoku.problemdomain;

import com.sudoku.constants.GameState;
import com.sudoku.utilities.Helper;

import java.io.Serializable;

/** Class that represents a Sudoku game. */
public class Sudoku implements Serializable {
    public static final int LIMIT = 9;
    private final GameState gameState;
    private final int[][] grid;

    public Sudoku(GameState gameState, int[][] grid) {
        this.gameState = gameState;
        this.grid = grid;
    }

    public int[][] getGridCopy() {
        return Helper.copyToNewArray(grid);
    }

    public GameState getGameState() {
        return gameState;
    }
}