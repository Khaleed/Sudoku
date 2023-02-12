package com.sudoku.storage;

import com.sudoku.problemdomain.Sudoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorageImpl implements StorageInterface {
    private static final File file = new File(System.getProperty("user.home"), "sudokudata.txt");

    @Override
    public void storeData(Sudoku sudoku) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(sudoku);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new IOException("Unable to store data requested");
        }
    }

    @Override
    public Sudoku retrieveData() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            return (Sudoku) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Unable to get data requested");
        }
    }
}
