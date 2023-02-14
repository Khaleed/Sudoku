package com.sudoku.storage;

import com.sudoku.model.Sudoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Class that implements read and writes to a file. */
public class FileStorageImpl implements StorageInterface {
    private static final File FILE = new File(System.getProperty("user.home"), "sudokudata.txt");

    @Override
    public void storeData(Sudoku sudoku) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(sudoku);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Unable to store data requested");
        }
    }

    @Override
    public Sudoku retrieveData() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            return (Sudoku) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Unable to get data requested");
        }
    }
}
