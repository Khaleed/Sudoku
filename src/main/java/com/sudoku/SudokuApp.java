package com.sudoku;

import com.sudoku.gamelogic.SudokuBuilder;
import com.sudoku.userinterface.UserInterfaceContract;
import com.sudoku.userinterface.UserInterfaceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        UserInterfaceContract.View userInterfaceImpl = new UserInterfaceImpl(primaryStage);
        try {
            SudokuBuilder.build(userInterfaceImpl);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw ioException;
        }
    }
}
