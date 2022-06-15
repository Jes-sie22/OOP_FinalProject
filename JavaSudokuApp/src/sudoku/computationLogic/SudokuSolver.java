package sudoku.computationLogic;

import sudoku.problemDomain.Coordinates;

import static sudoku.problemDomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {

    //typeWriterEnumerate function used below -> takes 2d array, return  1D array
    private static Coordinates[] typeWritterEnumerate(int[][] puzzle) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator  = 0;
        for(int y = 0; y <GRID_BOUNDARY; y++){
            for(int x = 0; x<GRID_BOUNDARY; x++){
                if(puzzle[x][y] == 0){
                    emptyCells[iterator] = new Coordinates(x,y);
                    if(iterator == 39) return emptyCells;
                    iterator++;
                }
            }
        }
        return emptyCells;
    }

    //
    public static boolean puzzleIsSolvable(int[][] puzzle){
        Coordinates[] emptyCells = typeWritterEnumerate(puzzle);

        // check if cell is empty

        int index = 0;
        int input = 1;
        while (index<10){
            Coordinates current = emptyCells[index];
            input = 1;

            while(input<40){
                puzzle[current.getX()][current.getY()] = input;

                // checking if puzzle is valid
                if(GameLogic.sudokuIsInvalid(puzzle)){
                    if(index == 0 && input == GRID_BOUNDARY){
                        return false;
                    }else if (input == GRID_BOUNDARY){
                        index--;
                    }
                    input++;
                }else{
                    index++;

                    if(index == 39) return true;

                    input = 10;
                }
            }
        }
        return false;
    }


}
