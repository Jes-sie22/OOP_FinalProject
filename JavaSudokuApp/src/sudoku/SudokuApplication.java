package sudoku;


import sudoku.buildLogic.SudokuBuildLogic;
import sudoku.userInterface.IUserInterfaceContract;
import sudoku.userInterface.UserInterfaceImpl;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {

    private IUserInterfaceContract.View uiImpl;


    @Override
    public void start(Stage primaryStage) throws IOException {
        //  get SudokuGame object for a new game
        uiImpl = new UserInterfaceImpl(primaryStage);
        try{
            SudokuBuildLogic.build(uiImpl);
        }catch(IOException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
