package com.sudoku.problemdomain;

import java.io.IOException;

public interface RecordInterface {
    void updateGameRecord(Sudoku sudoku) throws IOException;
    Sudoku getGameRecord() throws IOException;
}
