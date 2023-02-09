package com.sudoku.userinterface;

import com.sudoku.problemdomain.Sudoku;

public class UserInterfaceContract {
    public interface EventListener {
        void onInput(int x, int y, int input);
        void showDialog();
    }

    public interface View {
        void setListener(UserInterfaceContract.EventListener listener);
        void placeOnSquare(int x, int y, int input);
        void updateGameBoard(Sudoku sudoku);
        void printDialog(String dialog);
        void printError(String err);
    }
}
