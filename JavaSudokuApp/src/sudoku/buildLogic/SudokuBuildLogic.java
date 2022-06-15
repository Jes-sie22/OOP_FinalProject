package sudoku.buildLogic;

import sudoku.computationLogic.GameLogic;
import sudoku.controlLogic.*; // imports  ControlLogic class  -> importing control logic right away gives an error idk why
import sudoku.dataStorage.LocalStorageImpl;
import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;
import sudoku.userInterface.IUserInterfaceContract;

import java.io.IOException;
// separating configuration from use =>
// an object which uses other object should not also build those objects
// this function/class is the logic to build the object -> which object ? for the game
public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) throws IOException{
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();


        // error handling: attempt to get game data from storage (if exist)
        // if not sucessful -> throw IOException, ask for new game
        try{
            initialState =  storage.getGameData();

        }catch(IOException e){
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        // creating controllogic class -> for UI
        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage,userInterface);


        // binding control logic to UI to interact / communicate
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);

    }
}
















