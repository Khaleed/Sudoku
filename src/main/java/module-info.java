
module sudoku {
    // Transitively requires javafx.base and javafx.controls.
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;

    exports com.sudoku to javafx.graphics;
}