package com.sudoku.storage;

import com.sudoku.model.Sudoku;

import java.io.IOException;

/** Reads and writes data. */
public interface StorageInterface {
    Sudoku retrieveData() throws IOException;
    void storeData(Sudoku sudoku) throws IOException;
}
