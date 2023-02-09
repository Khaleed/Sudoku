package com.sudoku.userinterface.controllogic;

import com.sudoku.constants.Dialog;
import com.sudoku.constants.GameState;
import com.sudoku.gamelogic.GameLogic;
import com.sudoku.problemdomain.RecordInterface;
import com.sudoku.problemdomain.Sudoku;
import com.sudoku.userinterface.UserInterfaceContract;

import java.io.IOException;

/** Class that responds to commands from user to update UI and back-end. */
public class ControlLogic implements UserInterfaceContract.EventListener {
    private RecordInterface recordInterface;
    private UserInterfaceContract.View view;

    @Override
    public void onInput(int x, int y, int input) {
        try {
            // Update copy of current game data.
            Sudoku sudokuData = recordInterface.getGameRecord();
            int[][] newSudokuData = sudokuData.getGridCopy();
            newSudokuData[x][y] = input;
            sudokuData = new Sudoku(GameLogic.finishedGame(newSudokuData), newSudokuData);
            recordInterface.updateGameRecord(sudokuData);
            // Change UI.
            view.placeOnSquare(x, y, input);
            // Check game is complete.
            if (sudokuData.getGameState() == GameState.FINISHED) {
                view.printDialog(Dialog.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.printDialog(Dialog.ERROR);
        }
    }

    @Override
    public void showDialog() {
        try {
            recordInterface.updateGameRecord(GameLogic.getNewGame());
            view.updateGameBoard(recordInterface.getGameRecord());
        } catch(IOException e) {
            e.printStackTrace();
            view.printError(Dialog.ERROR);
        }
    }
}
