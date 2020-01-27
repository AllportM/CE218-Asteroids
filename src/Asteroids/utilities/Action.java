package Asteroids.utilities;

public class Action {
    public int thrust = 0;
    public int turn = 0;
    public boolean shoot = false;

    public String toString()
    {
        return String.format("Thrust = %d, Turn = %d, Shoot = %s", thrust, turn, shoot);
    }
}
