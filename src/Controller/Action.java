package Controller;

/**
 * Action's purpose is to provide control variables for maneuvering a Ship object through thrust, turn
 * and shoot actions
 */
public class Action {
    public int thrust = 0; // tells the ship to thrust if 1, negative thrust if -1, or none if 0
    public int turn = 0; // tells the shp to turn right if 1, left if -1, or nothing if 0
    public boolean shoot = false; // tells the ship to fire if true

    public String toString()
    {
        return String.format("Thrust = %d, Turn = %d, Shoot = %s", thrust, turn, shoot);
    }
}
