package com.sudoku;

import com.sudoku.gamelogic.SudokuBuilder;
import com.sudoku.view.UserInterfaceContract;
import com.sudoku.view.UserInterfaceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApp extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Actual user interface.
        UserInterfaceContract.View userInterfaceImpl = new UserInterfaceImpl(primaryStage);
        try {
            SudokuBuilder.build(userInterfaceImpl);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
