package com.sudoku.userinterface;

import com.sudoku.constants.GameState;
import com.sudoku.problemdomain.Coordinates;
import com.sudoku.problemdomain.Sudoku;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class UserInterfaceImpl implements UserInterfaceContract.View, EventHandler<KeyEvent> {
    private final Stage stage;
    private final Group group;
    // Track 9 x 9 text fields.
    private Map<Coordinates, SudokuTextField> coordinatesSudokuTextFieldMap;
    private UserInterfaceContract.EventListener eventListener;
    // Styling boiler plate.
    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_COORDINATES = 600; // X and Y
    // Color.
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(150, 125, 0);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    // Title
    private static final String GAME_TITLE = "Sudoku";

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.group = new Group();
        this.coordinatesSudokuTextFieldMap = new HashMap<>();
        initUserInterface();
    }

    @Override
    public void setListener(UserInterfaceContract.EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void placeOnSquare(int x, int y, int input) {
        SudokuTextField square = coordinatesSudokuTextFieldMap.get(new Coordinates(x, y));
        String move = String.valueOf(input);
        if (move.equals("0")) {
            move = "";
        }
        square.textProperty().setValue(move);
    }

    @Override
    public void updateGameBoard(Sudoku sudoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField square = coordinatesSudokuTextFieldMap.get(new Coordinates(i, j));
                String val = String.valueOf(sudoku.getGridCopy()[i][j]);
                if (val.equals("0")) {
                    val = "";
                }
                square.setText(val);
                // Do we have a new game?
                if (sudoku.getGameState() == GameState.NEW) {
                    square.setStyle("-fx-opacity: 1;");
                    square.setDisable(false);
                } else {
                    square.setStyle("-fx-opacity: 0.7;");
                    square.setDisable(true);
                }
            }
        }
    }

    @Override
    public void printDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            eventListener.showDialog();
        }
    }

    @Override
    public void printError(String err) {
        Alert alert = new Alert(Alert.AlertType.ERROR, err, ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            String text = keyEvent.getText();
            if (text.matches("[0-9]")) {
                handleInput(Integer.parseInt(text), keyEvent.getSource());
            } else if (KeyCode.BACK_SPACE == keyEvent.getCode()) {
                handleInput(0, keyEvent.getSource());
            } else {
                ((TextField) keyEvent.getSource()).setText("");
            }
        }
        keyEvent.consume();
    }

    private void handleInput(int val, Object source) {
        eventListener.onInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                val
        );
    }

    private void initUserInterface() {
        renderBackground(group);
        renderTitle(group);
        renderGameBoard(group);
        renderTextFields(group);
        renderGridLines(group);
        stage.show();
    }

    private void renderGridLines(Group group) {
        int startCoordinates = 114;
        int i = 0;
        while (i < 8) {
            int lineWeight;
            if (i == 2 || i == 5) {
                lineWeight = 3;
            } else {
                lineWeight = 2;
            }
            Rectangle verticalLine =
                    getLine(startCoordinates + 64 * i, BOARD_PADDING, BOARD_COORDINATES, lineWeight);
            Rectangle horizontalLine =
                    getLine(BOARD_PADDING, startCoordinates + 64 * i, lineWeight, BOARD_COORDINATES);
            group.getChildren().addAll(verticalLine, horizontalLine);
            i++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }

    private void renderTextFields(Group group) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int delta = 64;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int x = xOrigin * i * delta;
                int y = yOrigin * j * delta;
                // Create and style square of the game board.
                SudokuTextField square = new SudokuTextField(i, j);
                styleSudokuSquare(square, x, y);
                // Handler for key inputs.
                square.setOnKeyPressed(this);
                // Store coordinates to textField square reference mappings.
                coordinatesSudokuTextFieldMap.put(new Coordinates(i, j), square);
                // Add to children of Group container.
                group.getChildren().add(square);
            }
        }
    }

    private void styleSudokuSquare(SudokuTextField square, double x, double y) {
        square.setFont(new Font(32));
        square.setAlignment(Pos.CENTER);
        square.setLayoutX(x);
        square.setLayoutY(y);
        square.setPrefHeight(64);
        square.setPrefWidth(64);
        square.setBackground(Background.EMPTY);
    }

    private void renderGameBoard(Group group) {
        Rectangle board = new Rectangle();
        board.setX(BOARD_PADDING);
        board.setY(BOARD_PADDING);
        board.setWidth(BOARD_COORDINATES);
        board.setHeight(BOARD_COORDINATES);
        board.setFill(BOARD_BACKGROUND_COLOR);
        group.getChildren().addAll(board);
    }

    private void renderTitle(Group group) {
        Text text = new Text(235, 690, GAME_TITLE);
        text.setFill(Color.ROYALBLUE);
        text.setFont(new Font(43));
        group.getChildren().add(text);
    }

    private void renderBackground(Group group) {
        Scene scene = new Scene(group, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }
}
