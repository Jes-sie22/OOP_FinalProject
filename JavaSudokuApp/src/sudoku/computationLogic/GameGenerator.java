/* Algorithm to solve the game
* utilizes static methods -> similar to regular functions */

package sudoku.computationLogic;

import sudoku.problemDomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemDomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
    }

    // how to create a solvable sudoku puzzle
    private static int[][] unsolveGame(int[][] solvedGame) {
        // how do we know if a sudoku game is solvable or not?
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while(solvable == false){
            SudokuUtilities.copySudokuArrayValues(solvedGame,solvableArray);

            // removing 40 random numbers from soved sudoku board
            int index = 0;
            while(index<40){
                // randomly pick coordinates
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                // check to see if a value is removed in a spcific grid
                if(solvableArray[xCoordinate][yCoordinate]!=0){
                    // set it to 0 -> empty
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            // attempt to solve puzzle again
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY]; // create an immutable copy of the grid
            SudokuUtilities.copySudokuArrayValues(solvableArray,toBeSolved);

            // check if puzzle is solvable with sudoku solver class
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);

        }
        return solvableArray;
    }

    // generate solved sudoku game
    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis()) ; // random number generator
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY]; // create new grid

        // allocate the values [1-9] 9 times on the board
        for (int value = 1; value<= GRID_BOUNDARY; value++ ){
            int allocations = 0; // number of times a number is allocated
            int interrupt = 0; // for backtracking -> if allocation too many -> interrupt ++

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;

            // backtracking algorithm
            while (allocations < GRID_BOUNDARY){
                if(interrupt> 200){
                    // reset the past numbers allocated to 0
                    allocTracker.forEach(coord ->{
                        newGrid[coord.getX()][coord.getY()] = 0;
                    } );

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if(attempts>500){
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                // randomly selecting a tile with RNG
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate  = random.nextInt(GRID_BOUNDARY);

                // check if randomly selected coordinate is blank
                if(newGrid[xCoordinate][yCoordinate]==0){
                    // allocate value
                    newGrid[xCoordinate][yCoordinate] = value;

                    // check if allocated value is invalid
                    if (GameLogic.sudokuIsInvalid(newGrid)){
                        // reset tile
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;

                    }

                    else{ // if value is valid
                        // add to allocation tracker array
                        allocTracker.add(new Coordinates(xCoordinate,yCoordinate));
                        allocations++;
                    }

                }

            }

        }
        return newGrid;
    }

    // clear the array -> traverse array and reset each element to 0
    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex<GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex<GRID_BOUNDARY; yIndex++){
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
