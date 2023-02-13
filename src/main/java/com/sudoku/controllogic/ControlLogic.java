package com.sudoku.controllogic;

import com.sudoku.constants.Dialog;
import com.sudoku.constants.GameState;
import com.sudoku.gamelogic.GameLogic;
import com.sudoku.storage.StorageInterface;
import com.sudoku.model.Sudoku;
import com.sudoku.view.UserInterfaceContract;

import java.io.IOException;

/** Class that responds to commands from user to update UI and back-end. */
public class ControlLogic implements UserInterfaceContract.EventListener {
    private final StorageInterface storageInterface;
    private final UserInterfaceContract.View view;

    public ControlLogic(StorageInterface storageInterface, UserInterfaceContract.View view) {
        this.storageInterface = storageInterface;
        this.view = view;
    }

    @Override
    public void onInput(int x, int y, int input) {
        try {
            // Update copy of current game data.
            Sudoku sudokuData = storageInterface.retrieveData();
            int[][] newSudokuData = sudokuData.getGridCopy();
            newSudokuData[x][y] = input;
            sudokuData = new Sudoku(GameLogic.finishedGame(newSudokuData), newSudokuData);
            storageInterface.storeData(sudokuData);
            // Change UI.
            view.placeOnSquare(x, y, input);
            // Check game is complete.
            if (sudokuData.getGameState() == GameState.FINISHED) {
                view.printDialog(Dialog.GAME_WON);
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.printDialog(Dialog.ERROR);
        }
    }

    @Override
    public void showDialog() {
        try {
            storageInterface.storeData(GameLogic.getNewGame());
            view.updateGameBoard(storageInterface.retrieveData());
        } catch(IOException e) {
            e.printStackTrace();
            view.printError(Dialog.ERROR);
        }
    }
}
