package sudoku.userInterface;

import sudoku.problemDomain.SudokuGame;

// using parent interface as a namespace
public interface IUserInterfaceContract {
    // child interface eventListener
    interface EventListener{
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();

    }

    interface View{
        void setListener(IUserInterfaceContract.EventListener listener);
        void updateSquare(int x ,int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String Message);
        void showError(String message);

    }
}
