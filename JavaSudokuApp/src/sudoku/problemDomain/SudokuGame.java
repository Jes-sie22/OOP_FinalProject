package sudoku.problemDomain;

import sudoku.computationLogic.SudokuUtilities;
import sudoku.constants.GameState;

import java.io.Serializable;

public class SudokuGame implements Serializable { // Serializable -> easily read and write java file to application
    private final GameState gameState; // gamestate custom data type ? create next lesson- enum
    private final int[][] gridState; // sudoku grid represented as 2D array

    // CONSTANT
    public static final int GRID_BOUNDARY = 9;

    // CONSTRUCTOR METHOD
    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    // GETTER METHODS
    public GameState getGameState() {
        return gameState;
    }
    // return copy of grid state -> protect the actual sudoku game object from being mutable
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState); // create class 'SudokuUtilities' with a method 'copyToNewArray'
    }
}
