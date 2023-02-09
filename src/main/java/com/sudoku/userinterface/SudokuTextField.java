package com.sudoku.userinterface;

import javafx.scene.control.TextField;

/** Class that allows user to enter a single line of unformatted text. */
public class SudokuTextField extends TextField {
    private final int x;
    private final int y;

    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // To avoid repeat numbers when user enters input on text field.
        if (!text.matches("[0-9]")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        if (!replacement.matches("[0-9]")) {
            super.replaceSelection(replacement);
        }
    }
}
