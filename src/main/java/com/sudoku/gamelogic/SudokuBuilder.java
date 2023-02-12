package com.sudoku.gamelogic;

import com.sudoku.storage.FileStorageImpl;
import com.sudoku.storage.StorageInterface;
import com.sudoku.problemdomain.Sudoku;
import com.sudoku.userinterface.UserInterfaceContract;
import com.sudoku.userinterface.controllogic.ControlLogic;

import java.io.IOException;

public class SudokuBuilder {
    public static void build(UserInterfaceContract.View ui) throws IOException {
        Sudoku initGame;
        StorageInterface storage = new FileStorageImpl();

        try {
            initGame = storage.retrieveData();
        } catch (IOException io) {
            initGame = GameLogic.getNewGame();
            storage.storeData(initGame);
        }
        UserInterfaceContract.EventListener eventListener = new ControlLogic(storage, ui);
        ui.setListener(eventListener);
        ui.updateGameBoard(initGame);
    }
}
