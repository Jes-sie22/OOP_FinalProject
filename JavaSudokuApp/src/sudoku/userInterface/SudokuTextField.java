package sudoku.userInterface;

import javafx.scene.control.TextField;

// sub class to TextField class from java-fx
public class SudokuTextField extends TextField {
    private final int x;
    private final int y;

    // CONSTRUCTOR
    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // GETTER METHDOS
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // overriding 2 functions (replaceText,replaceSelection) to replace numbers in sudoku correctly
    @Override
    public void replaceText(int i, int i1, String s){
        // check if string s matches the regular expression [0-9]
        if(!s.matches("[0-9]")){
            super.replaceText(i,i1,s); // super - calls method from parent class
        }
    }
    @Override
    public void replaceSelection(String s){
        if(!s.matches("[0-9]")){
            super.replaceSelection(s);
        }
    }
}













