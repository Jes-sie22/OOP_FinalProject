package sudoku.dataStorage;

import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;

import java.io.*;



// actual folder = persistence
// writes a file to the local file system to store game data
public class LocalStorageImpl implements IStorage {
    // file reference variable
    private static File GAME_DATA = new File(
            System.getProperty("user.home"),"gameData.txt");
        // system.getProperty() -> retrives operating system's home directory
        // in home directory, store gameData.txt file




    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try{
            // write file
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(game);

            objectOutputStream.close();


        }catch (IOException e){

            throw new IOException("Unable to access Game Data");

        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        // reading data -> input stream ()
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        // if cant locate file bcs first time running application
        try{
            SudokuGame gameState  = (SudokuGame) objectInputStream.readObject();

            objectInputStream.close();
            return gameState;
        }catch (ClassNotFoundException e){

            throw  new IOException("File not found ");
        }











    }
}
