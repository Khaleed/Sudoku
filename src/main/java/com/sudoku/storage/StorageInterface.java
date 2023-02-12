package com.sudoku.storage;

import com.sudoku.problemdomain.Sudoku;

import java.io.IOException;

public interface StorageInterface {
    void storeData(Sudoku sudoku) throws IOException;
    Sudoku retrieveData() throws IOException;
}
