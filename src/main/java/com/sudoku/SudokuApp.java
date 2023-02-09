package com.sudoku;

import com.sudoku.userinterface.UserInterfaceContract;
import com.sudoku.userinterface.UserInterfaceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApp extends Application {
    private UserInterfaceContract.View userInterfaceImpl;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        userInterfaceImpl = new UserInterfaceImpl(primaryStage);
        try {
            SodokuGameLogic.build(userInterfaceImpl);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw ioException;
        }
    }
}
