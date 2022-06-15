package sudoku.problemDomain;

import java.util.Objects;

public class Coordinates {
    private final int x;
    private final int y;

    // METHODS
    // CONSTRUCTOR
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // GETTER METHODS
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // methods/functions for hash-map implementation
    // class extends from object
    // to store coordinate object in a hash map
    // use as keys to keep track of UI elements
    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x== that.x &&
                y == that.y;

    }

    // hash code function
    // hash code -> unique identifier generated from data given
    @Override
    public int hashCode(){
        // Objects.hash function generates a unique identifier from specific x and y values
        return Objects.hash(x,y);
    }
}
