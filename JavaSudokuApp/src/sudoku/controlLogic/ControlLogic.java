package sudoku.controlLogic;

import sudoku.computationLogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;
import sudoku.userInterface.IUserInterfaceContract;

import java.io.IOException;

public class ControlLogic implements IUserInterfaceContract.EventListener {

    private IStorage storage;
    private IUserInterfaceContract.View view;


    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    // implementing interface methods
    public void onSudokuInput(int x, int y, int input) {
        try{
            // write to game storage when user inputs or deletes a number
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;

            // create a new SudokuGame obj
            gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState);

            storage.updateGameData(gameData);

            //update the square
            view.updateSquare(x,y,input);

            // checking if gamestate is complete
            if(gameData.getGameState() == GameState.COMPLETE){
                // show message to user
                view.showDialog(Messages.GAME_COMPLETE);
            }

        }catch(IOException e){
            e.printStackTrace();
            // show error message for error
            view.showError(Messages.ERROR);


        }
    }

    @Override
    // if user clicks OK button
    public void onDialogClick() {
        try{
            storage.updateGameData(GameLogic.getNewGame( ));

            // update board
            view.updateBoard(storage.getGameData());

        }catch(IOException e){
            view.showError(Messages.ERROR);

        }
    }
}
