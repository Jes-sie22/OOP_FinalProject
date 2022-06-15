package sudoku.computationLogic;

import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemDomain.SudokuGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static sudoku.problemDomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {

    public static SudokuGame getNewGame(){
        return new SudokuGame(GameState.NEW,GameGenerator.getNewGameGrid());
    }

    // check if game is complete
    public static GameState checkForCompletion (int[][] grid){
        if(sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if(tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    public static boolean sudokuIsInvalid(int[][] grid) {
        if(rowsAreInvalid(grid)) return true;
        if(colsAreInvalid(grid)) return true;
        if(squaresAreInvaid(grid)) return true;
        else return false;
    }

    private static boolean rowsAreInvalid(int[][] grid) {
        for (int yIndex = 0; yIndex<GRID_BOUNDARY; yIndex++){
            List<Integer> row = new ArrayList<>();
            for(int xIndex = 0 ; xIndex<GRID_BOUNDARY; xIndex++){
                row.add(grid[xIndex][yIndex]);
            }

            if(collectionHasRepeats(row)) return true;
        }
        return false;
    }

    private static boolean colsAreInvalid(int[][] grid) {
        for (int xIndex = 0; xIndex<GRID_BOUNDARY; xIndex++){
            List<Integer> row = new ArrayList<>();
            for(int yIndex = 0 ; yIndex<GRID_BOUNDARY; yIndex++){
                row.add(grid[xIndex][yIndex]);
            }

            if(collectionHasRepeats(row)) return true;
        }
        return false;


    }

    // checking individual rows of squares -> using horizontal blocks using top middle bottom (TMB) method
//    https://www.youtube.com/watch?v=URHZiLu1-LI
    private static boolean squaresAreInvaid(int[][] grid) {
        if(rowOfSquareIsInvalid(Rows.TOP,grid)) return true;
        if(rowOfSquareIsInvalid(Rows.MIDDLE,grid)) return true;
        if(rowOfSquareIsInvalid(Rows.BOTTOM,grid)) return true;

        return false;
    }

    private static boolean rowOfSquareIsInvalid(Rows value, int[][] grid) {
        switch (value){
            // checking 3 squares in the top row
            case TOP:
                if(squareIsInvaid(0,0,grid)) return true;
                if(squareIsInvaid(0,3,grid)) return true;
                if(squareIsInvaid(0,6,grid)) return true;
                return false;
            case MIDDLE:
                if(squareIsInvaid(3,0,grid)) return true;
                if(squareIsInvaid(3,3,grid)) return true;
                if(squareIsInvaid(3,6,grid)) return true;
                return false;
            case BOTTOM:
                if(squareIsInvaid(6,0,grid)) return true;
                if(squareIsInvaid(6,3,grid)) return true;
                if(squareIsInvaid(6,6,grid)) return true;
                return false;

            default:
                return false;

        }
    }

    // check if individual 3x3 square is invalid
    private static boolean squareIsInvaid(int xIndex, int yIndex, int[][] grid) {
        int xIndexEnd = xIndex +3;
        int yIndexEnd = yIndex + 3;

        // adding the sudoku square values into a List
        List<Integer> square = new ArrayList<>();
        while (yIndex<yIndexEnd){
            while (xIndex < xIndexEnd){
                square.add(grid[xIndex][yIndex]);
                xIndex++;
            };

            xIndex -=3; // reset x back to left most tile of that row
            yIndex++;

        }

        if(collectionHasRepeats(square)) return true;
        else return false;


    }

    public static boolean collectionHasRepeats(List<Integer> collection) {
        for(int index = 1; index <= GRID_BOUNDARY; index++){
            // checking if the numbers [1,9] are repreated
            // collections.frequency -> counts number of times an element shows up
            if(Collections.frequency(collection, index)>1) return true;
        }
        return false;

    }


    public static boolean tilesAreNotFilled(int[][] grid) {
        for(int xIndex = 0; xIndex <GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex <GRID_BOUNDARY; yIndex++){
                if(grid[xIndex][yIndex] == 0 ) return true;
            }
        }
        return false;
    }
}
