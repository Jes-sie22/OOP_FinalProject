package sudoku.userInterface;

import javafx.scene.control.ButtonType;
import sudoku.constants.GameState;
import sudoku.problemDomain.Coordinates;
import sudoku.problemDomain.SudokuGame;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {
    private final Stage stage; // applicatiion window
    private final Group root; // a container like <div></div>

    private HashMap<Coordinates,SudokuTextField> textFieldCoordinates;// keeping track of 81 (9x9) different text fields with hash map

    private IUserInterfaceContract.EventListener listener; //interpret events (clicks etc) between frontend and backend

    private static final double WINDOW_X = 732;
    private static final double WINDOW_Y = 732;
    // window style information
    private static final double BOARD_PADDING = 50; // padding between window edge and sudoku board
    private static final double BOARD_X_AND_Y = 576;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(137, 171, 237);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(252, 246, 245);
    private static final String SUDOKU = "Sudoku OOP"; // heading

    // CONSTRUCTOR
    public UserInterfaceImpl(Stage stage) {
        // initializing attributes for the application window
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    // helper functions for each UI element
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);

        // reveal user interface
        stage.show();
    }

    private void drawGridLines(Group root) {
        int xAndY = 114; // start coordinate of drawing gridlines
        int index = 0;
        // loop to draw 8 lines
        while (index < 8){

            // adjusting line thickness
            int thickness;
            // increasing line thickness for the ones separating a 3x3 block
            if(index == 2 || index == 5){
                thickness = 3;
            }
            else{
                thickness = 2;
            }

            // drawing the grid lines w Rectagle class from java fx
            Rectangle verticalLine = getLine(xAndY + 64 * index, BOARD_PADDING, BOARD_X_AND_Y, thickness);
            Rectangle horizontalLine = getLine(BOARD_PADDING,xAndY + 64 * index, thickness,BOARD_X_AND_Y);


            // adding UI elements to root (container)
            root.getChildren().addAll(verticalLine,horizontalLine);

            //increment index
            index++;
        }
    }

    // helper method
    private Rectangle getLine(double x, double y, double height, double width){
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        // styling the line/ rectangle
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);

        return line;

    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;

        final int xAndYDelta = 64; // change in x and y value -> draw elements in increments of 64 pixels(i did the math)

        // O(n^2) runtime complexity
        for (int xIndex = 0; xIndex<9; xIndex++){
            for (int yIndex = 0; yIndex<9; yIndex++){
                // calculating coordinates
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                // placing a text field in the calculated coordinates
                SudokuTextField tile = new SudokuTextField(xIndex,yIndex);

                // styling sudoku textfields
                styleSudokuTile(tile, x,y);

                // listening for user events/ actions
                tile.setOnKeyPressed(this);

                // add text fields to hashmap with coordinates object as the key
                textFieldCoordinates.put(new Coordinates(xIndex,yIndex), tile);
                root.getChildren().add(tile);
            }
        }

    }

    // helper method for tile styling
    private void styleSudokuTile(SudokuTextField tile, double x, double y){
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY); // transparent tile background



    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().addAll(boardBackground);

    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);

        title.setFill(Color.WHITE); // setfill for font color
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X,WINDOW_Y); // scene = view group aka a page
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }




    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    @Override
    // updateSquare called when user has input number , dont hv to update whole board, single UI element only
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile =  textFieldCoordinates.get(new Coordinates(x,y));

        String value = Integer.toString(input);

        if(value.equals("0")) value = "";

        tile.textProperty().setValue(value);

    }

    @Override
    // updateBoard -> updates the ENTIRE board, when user finishes the game
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++){
            for (int yIndex = 0; yIndex < 9; yIndex++){

                // get from hashmap coordinates for each text field
                // CHANGED TEXT FIELD TYPE -> SUDOKU TEXT FIELD
                SudokuTextField tile = textFieldCoordinates.get(new Coordinates(xIndex,yIndex));

                String value = Integer.toString(game.getCopyOfGridState()[xIndex][yIndex]); // getCopyOfGridState create an immutable copy of game

                // set empty tiles on board
                if(value.equals("0")) value = "";
                tile.setText(value);

                // check if new game is created
                if(game.getGameState() == GameState.NEW){
                    // make empty tiles translucent, and enable text field
                    if (value.equals("")){
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    }
                    // make numbered tiles opaque, and disable text field
                    else{
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);

                    }
                }


            }
        }


    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }


    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    @Override
    // event handing from javafx interface
    public void handle(KeyEvent event) {

        // check if user pressed a key
        if(event.getEventType() == KeyEvent.KEY_PRESSED){

            // check for number key pressess
            if(event.getText().matches("[0-9]")){
                int value = Integer.parseInt(event.getText()); // value = user input number
                handleInput(value, event.getSource()); // source = UI element clicked on -> passed as object

            }
            // check for delete key presses
            else if(event.getCode() == KeyCode.BACK_SPACE){
                handleInput(0, event.getSource());

            }else {
                ((TextField) event.getSource()).setText("");

            }
        }
        // stops propagating event to teh rest of application
        event.consume();

    }

    // passing user input to controlLogic class
    private void handleInput(int value, Object source) {
        // identify the coordinates where user has input
        listener.onSudokuInput(((SudokuTextField) source).getX(),  ((SudokuTextField) source).getY(), value);
    }
}
